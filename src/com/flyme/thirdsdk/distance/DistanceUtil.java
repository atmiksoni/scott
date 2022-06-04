package com.flyme.thirdsdk.distance;

public class DistanceUtil {
	// private static double EARTH_RADIUS = 6378.137;
	private static double EARTH_RADIUS = 6371.393;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算两个经纬度之间的距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(String lat1, String lng1, String lat2, String lng2) {
		double beginlat=Double.parseDouble(lat1);
		double beginlng=Double.parseDouble(lng1);
		double endlat=Double.parseDouble(lat2);
		double endlng=Double.parseDouble(lng2);
		double radLat1 = rad(beginlat);
		double radLat2 = rad(endlat);
		double a = radLat1 - radLat2;
		double b = rad(beginlng) - rad(endlng);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 1000);
		return s;
	}

	public static void main(String[] args) {
		System.out.println(DistanceUtil.GetDistance("29.490295", "106.486654", "29.615467", "106.581515"));
	}
}
