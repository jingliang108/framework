package com.tanjin.framework.base.common.utils;

import com.alibaba.fastjson.JSON;

public class LogFormatUtil {

	public static String getActionFormat(String actionName, String phone, Integer cusId, Integer status) {
		String statusStr = status == 1 ? "开始" : status == 2 ? "结束" : status == 3 ? "成功" : "失败";
		String str = "*****[%s %s %s %s]*****";
		str = String.format(str, actionName, phone, cusId, statusStr);
		return str;
	}

	public static String getActionFormat(String actionName, String key, Integer status) {
		String statusStr = status == 1 ? "开始" : status == 2 ? "结束" : status == 3 ? "成功" : "失败";
		String str = "*****[%s %s %s]*****";
		str = String.format(str, actionName, key, statusStr);
		return str;
	}

	public static String getActionFormat(String actionName, Integer status) {
		String statusStr = status == 1 ? "开始" : status == 2 ? "结束" : status == 3 ? "成功" : "失败";
		String str = "*****[%s %s]*****";
		str = String.format(str, actionName, statusStr);
		return str;
	}

	public static String getActionFormat(String actionName, String phone, Integer cusId, String action) {
		String str = "*****[%s %s %s %s]*****";
		str = String.format(str, actionName, phone, cusId, action);
		return str;
	}

	public static String getActionFormat(String actionName, String phone, String action) {
		String str = "*****[%s %s %s]*****";
		str = String.format(str, actionName, phone, action);
		return str;
	}

	public static String getActionFormat(String phone, Integer cusId, String action) {
		String str = "*****[%s %s %s]*****";
		str = String.format(str, phone, cusId, action);
		return str;
	}

	public static String getActionFormat(String key, String action) {
		String str = "*****[%s %s]*****";
		str = String.format(str, key, action);
		return str;
	}

	public static String getActionFormat(Integer key, String action) {
		String str = "*****[%s %s]*****";
		str = String.format(str, key, action);
		return str;
	}

	public static String getActionFormat(Integer key, String action, Integer status) {
		String statusStr = status == 1 ? "开始" : status == 2 ? "结束" : status == 3 ? "成功" : "失败";
		String str = "*****[%s %s %s]*****";
		str = String.format(str, key, action, statusStr);
		return str;
	}

	public static String getActionFormat(Integer key, String name, String action, Integer status) {
		String statusStr = status == 1 ? "开始" : status == 2 ? "结束" : status == 3 ? "成功" : "失败";
		String str = "*****[%s %s %s %s]*****";
		str = String.format(str, key, name, action, statusStr);
		return str;
	}

	public static String getActionFormat(String action) {
		String str = "*****[%s]*****";
		str = String.format(str, action);
		return str;
	}

	public static String getResultFormat(String actionName, String phone, Integer cusId, String action, Object obj) {
		String str = "*****[%s %s %s %s] & %s*****";
		str = String.format(str, actionName, phone, cusId, action, JSON.toJSONString(obj));
		return str;
	}

	public static String getActionErrorFormat(String actionName, String phone, Integer cusId, String action) {
		String str = "#####[%s %s %s %s]#####";
		str = String.format(str, actionName, phone, cusId, action);
		return str;
	}

	public static String getActionErrorFormat(String msg) {
		String str = "#####[%s]#####";
		str = String.format(str, msg);
		return str;
	}
}
