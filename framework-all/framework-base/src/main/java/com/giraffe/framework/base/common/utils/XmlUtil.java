package com.tanjin.framework.base.common.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;

public class XmlUtil {

	/**
	 * @Title: xml转为String类型
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws Exception
	 * @author LILEI
	 * @date 2016年3月1日 下午5:20:32
	 * @version V1.0
	 */
	public static String parseXmlToString(HttpServletRequest request) throws Exception {
		Map<String, String> map = parseXmlToMap(request);
		return JSON.toJSONString(map);
	}

	/**
	 * @Title: xml转为String类型
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws Exception
	 * @author LILEI
	 * @date 2016年3月1日 下午5:20:32
	 * @version V1.0
	 */
	public static String parseXmlToString(InputStream inputStream) throws Exception {
		Map<String, String> map = parseXmlToMap(inputStream);
		return JSON.toJSONString(map);
	}

	/**
	 * @Title: XML 转为 实体
	 * @Description: TODO
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @author LILEI
	 * @date 2016年3月1日 下午5:20:48
	 * @version V1.0
	 */
	public static <T> T parseXmlToEntity(HttpServletRequest request, Class<T> clazz) throws Exception {
		Map<String, String> map = parseXmlToMap(request);
		String jsonStr = JSON.toJSONString(map);
		return JSON.parseObject(jsonStr, clazz);
	}

	/**
	 * @Title: XML转为 map 类型
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws Exception
	 * @author LILEI
	 * @date 2016年3月1日 下午5:21:00
	 * @version V1.0
	 */
	public static Map<String, String> parseXmlToMap(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream;
		inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Object> elementList = root.elements();
		// 遍历所有子节点
		for (Object obj : elementList) {
			Element e = (Element) obj;
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	public static Map<String, String> parseXmlToMap(InputStream inputStream) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Object> elementList = root.elements();
		// 遍历所有子节点
		for (Object obj : elementList) {
			Element e = (Element) obj;
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

}
