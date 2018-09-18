package com.tanjin.framework.base.common.utils;

public class DistanceUtil {

	private static final double EARTH_RADIUS = 6378137;// 赤道周长

	/**
	 * @Title: 获取2个经纬度之间的距离
	 * @Description: TODO
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 * @author LILEI
	 * @date 2016年3月29日 下午10:50:43
	 * @version V1.0
	 */
	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * @Title: 两个经纬度的距离 和 max 进行比较, 大于返回false 小于等于返回true
	 * @Description: TODO
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @param distance
	 *            距离最大值
	 * @return
	 * @author LILEI
	 * @date 2016年3月29日 下午10:52:20
	 * @version V1.0
	 */
	public static boolean getDistance(double lng1, double lat1, double lng2, double lat2, double max) {
		// 得到两个经纬度之间的距离
		Double distance = DistanceUtil.getDistance(lng1, lat1, lng2, lat2);
		// 和max进行比较
		return distance <= max ? true : false;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
