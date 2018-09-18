package com.giraffe.framework.base.common.utils;

public class CoordinateConversionUtil {
	private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	/**
	 * gg_lat 纬度 gg_lon 经度 GCJ-02转换BD-09 Google地图经纬度转百度地图经纬度
	 */
	public static Point google_bd_encrypt(double gg_lat, double gg_lon) {
		Point point = new Point();
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		point.setLat(bd_lat);
		point.setLng(bd_lon);
		return point;
	}

	/**
	 * wgLat 纬度 wgLon 经度 BD-09转换GCJ-02 百度转google
	 */
	public static Point bd_google_encrypt(double bd_lat, double bd_lon) {
		Point point = new Point();
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		point.setLat(gg_lat);
		point.setLng(gg_lon);
		return point;
	}

}
