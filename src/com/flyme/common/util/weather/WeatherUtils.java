package com.flyme.common.util.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.json.JsonUtils;

import net.sf.json.JSONObject;

/**
 * 得到未来6天的天气(含今天)
 */
public class WeatherUtils {

	private static URLConnection connectionData;

	public static Weather getWeather(String Cityid) {
		StringBuilder sb = null;
		BufferedReader br;// 读取data数据流
		JSONObject jsonData;
		JSONObject info;
		// 连接中央气象台的API
		URL url;
		try {
			url = new URL("http://m.weather.com.cn/data/" + Cityid + ".html");
			connectionData = url.openConnection();
			connectionData.setConnectTimeout(1000);
			br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
			sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Weather weather = new Weather();
		if (!ObjectUtils.isEmpty(sb)) {
			String datas = sb.toString();

			jsonData = JSONObject.fromObject(datas);
			info = jsonData.getJSONObject("weatherinfo");
			weather = JsonUtils.toBean(info, Weather.class);
			// 得到1到6天的天气情况
			List<WeatherDay> weatherDays = ObjectUtils.getArrayList();
			for (int i = 1; i <= 6; i++) {
				WeatherDay weatherDay = new WeatherDay();
				weatherDay.setFl(info.getString("fl" + i).toString());
				weatherDay.setSt(info.getString("st" + i).toString());
				weatherDay.setTemp(info.getString("temp" + i).toString());
				weatherDay.setTempF(info.getString("temp" + i).toString());
				weatherDay.setWeather(info.getString("weather" + i).toString());
				weatherDay.setWind(info.getString("wind" + i).toString());
				weatherDays.add(weatherDay);
			}
			weather.setWeatherDays(weatherDays);
		}
		return weather;
	}

	public static SimpleWeather getSimepleWeather(String Cityid) {
		SimpleWeather weather = new SimpleWeather();
		StringBuilder sb = null;
		BufferedReader br;// 读取data数据流
		JSONObject jsonData;
		JSONObject info;
		// 连接中央气象台的API
		URL url;
		try {
			url = new URL(" http://www.weather.com.cn/data/cityinfo/" + Cityid + ".html");
			connectionData = url.openConnection();
			connectionData.setConnectTimeout(5000);
			br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
			sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
		} catch (MalformedURLException e1) {
			weather.setCity("天气信息获取失败");
		} catch (IOException e) {
			weather.setCity("天气信息获取失败");
		}

		if (!ObjectUtils.isEmpty(sb)) {
			String datas = sb.toString();

			jsonData = JSONObject.fromObject(datas);
			info = jsonData.getJSONObject("weatherinfo");
			weather = JsonUtils.toBean(info, SimpleWeather.class);
			String img1 = null;
			String img2 = null;
			if (null != weather.getImg1()) {
				img1 = "c" + weather.getImg1().substring(1);
			}
			if (null != weather.getImg2()) {
				img2 = "c" + weather.getImg2().substring(1);
				if (img2.equals(img1)) {
					img2 = null;
				}
			}
			weather.setImg1(img1);
			weather.setImg2(img2);
		}
		return weather;
	}

	// 下载表示天气信息的图片
	// http://www.weather.com.cn/data/cityinfo/101180101.html
	public static String downloadImage(String imgName) throws Exception {
		String img = "http://m.weather.com.cn/img/" + imgName;
		URL url = new URL(img);
		HttpURLConnection httpurl = (HttpURLConnection) url.openConnection();
		httpurl.setRequestMethod("GET");
		httpurl.setConnectTimeout(100000);
		File file = new File(System.getProperty("user.dir") + "\\WebRoot\\res\\common\\images\\weather\\" + imgName);
		if (httpurl.getResponseCode() == 200) {
			FileOutputStream out = new FileOutputStream(file);
			InputStream input = url.openStream();
			int length = -1;
			byte[] bytes = new byte[1024];
			while ((length = input.read(bytes)) != -1) {
				out.write(bytes, 0, length);
			}
			out.close();
			input.close();
		}
		System.out.println(file.getAbsolutePath());
		return file.getAbsolutePath();
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 32; i++) {
				downloadImage("c" + i + ".gif");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}