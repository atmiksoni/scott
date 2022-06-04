package com.flyme.thirdsdk.baidu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.flyme.common.json.util.JsonUtil;
import com.flyme.thirdsdk.baidu.models.AddressComponent;
import com.flyme.thirdsdk.baidu.models.BaiduResult;

public class BaiduMapUtil {
	/**
	 * 根据地址获取经纬度
	 * 
	 * @param addr
	 *            查询的地址
	 * @return
	 * @throws IOException
	 */
	public static Object[] getCoordinate(String addr) throws IOException {
		String lng = null;// 经度
		String lat = null;// 纬度
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String key = "f247cdb592eb43ebac6ccd27f796e2d2";
		String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				int count = 1;
				while ((data = br.readLine()) != null) {
					if (count == 5) {
						lng = (String) data.subSequence(data.indexOf(":") + 1, data.indexOf(","));// 经度
						count++;
					} else if (count == 6) {
						lat = data.substring(data.indexOf(":") + 1);// 纬度
						count++;
					} else {
						count++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return new Object[] { lng, lat };
	}

	/**
	 * 根据经纬度获取省市区
	 * 
	 * @param addr
	 *            查询的地址
	 * @return
	 * @throws IOException
	 */
	public static AddressComponent getAddress(String lng, String lat) throws IOException {
		String ak = "A1dKKzrAXxHKoCImdUwS9jm4WVGspqFV";
		String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%s,%s&output=json&pois=1",ak, lat, lng);
		URL myURL = null;
		URLConnection httpsConn = null;
		AddressComponent addressComponent= new AddressComponent();
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = br.readLine();
				BaiduResult baiduResult = (BaiduResult) JsonUtil.jsonToBean(data, BaiduResult.class);
				addressComponent=baiduResult.getResult().getAddressComponent();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return addressComponent;
	}

	public static void main(String[] args) throws IOException {
		BaiduMapUtil map = new BaiduMapUtil();
		Object[] o = map.getCoordinate("郑州市花园路关虎屯");
		System.out.println(o[0]);// 经度
		System.out.println(o[1]);// 纬度
		AddressComponent addressComponent = map.getAddress("113.688929", "34.790897");
		System.out.println(addressComponent.getDistrict());
	}
}