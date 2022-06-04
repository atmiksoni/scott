package com.flyme.thirdsdk.gaode;

import com.flyme.common.util.context.PcUtils;
import com.flyme.common.util.http.HttpUtils;
import com.flyme.common.util.json.JsonUtils;

public class GaoDeUtils {
	private static final String ipurl = "http://restapi.amap.com/v3/ip?key=ef57ebaf361ae65776d2a99de150ecc3";
	private static final String cityurl = " http://restapi.amap.com/v3/config/district?key=ef57ebaf361ae65776d2a99de150ecc3&subdistrict=3";

	public static Location getLocation(String ip) {
		String json = HttpUtils.sendGet(ipurl + "&ip=" + ip, "UTF-8");
		Location location = JsonUtils.toBean(json, Location.class);
		return location;
	}

	public static String getCitys() {
		String json = HttpUtils.sendGet(cityurl, "UTF-8");
		return json;
	}

	public static Location getLocation() {
		String ip = PcUtils.getIpAddr();
		String json = HttpUtils.sendGet("http://restapi.amap.com/v3/ip?key=ef57ebaf361ae65776d2a99de150ecc3&ip=" + ip, "UTF-8");
		Location location = JsonUtils.toBean(json, Location.class);
		return location;
	}

	public static void main(String[] args) {

	}
}
