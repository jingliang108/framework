package com.tanjin.framework.base.common.utils;

/**
 * 对null的公共方法
 * 
 * @author LiuYiJun
 * */
public class NullUtil {
	/**
	 * 判断参数obj是否为null，如果obj为null,返回false；不为null，返回true
	 * 
	 * @author LiuYiJun
	 * */
	public static boolean isNotNull(Object obj) {
		return null != obj;
	}

	/**
	 * 判断参数obj是否为null，如果obj为null,返回true；不为null，返回false
	 * 
	 * @author LiuYiJun
	 * */
	public static boolean isNull(Object obj) {
		return null == obj;
	}
}
