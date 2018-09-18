package com.tanjin.framework.base.wechat.utils;

import java.util.Date;

public class WechatMessageUtil {
	
	public static String getTextMessage(String toUserId,String fromUserId,String  content){
		Date time = new Date();
		StringBuffer body = new StringBuffer();
		body.append("<xml>");
		body.append("<ToUserName><![CDATA[" +toUserId+ "]]></ToUserName>");
		body.append("<FromUserName><![CDATA[" + fromUserId+ "]]></FromUserName>");
		body.append("<CreateTime>" + time.getTime()+"</CreateTime>");
		body.append("<MsgType><![CDATA[text]]></MsgType>");
		body.append("<Content><![CDATA[" + content+"]]></Content>");
		body.append("</xml>");
		return body.toString();
	}
	
	public static String getImageMessage(String toUserId,String fromUserId,String  mediaId){
		Date time = new Date();
		StringBuffer body = new StringBuffer();
		body.append("<xml>");
		body.append("<ToUserName><![CDATA[" +toUserId+ "]]></ToUserName>");
		body.append("<FromUserName><![CDATA[" + fromUserId+ "]]></FromUserName>");
		body.append("<CreateTime>" + time.getTime()+"</CreateTime>");
		body.append("<MsgType><![CDATA[image]]></MsgType>");
		body.append("<Content>" + "</Content>");
		body.append("<Image>");
		body.append("<MediaId>"+mediaId+"</MediaId>");
		body.append("</Image>");
		body.append("<Content>" + "</Content>");
		body.append("</xml>");
		return body.toString();
	}

}
