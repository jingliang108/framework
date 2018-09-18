package com.giraffe.framework.base.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class OrderNoUtil {

	/**
	 * @Title: 生成27位数的交易号
	 * @Description: TODO
	 * @return
	 * @author LILEI
	 * @date 2016年3月21日 下午2:56:15
	 * @version V1.0
	 */
	public static synchronized String get27OrderNumber() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = localSimpleDateFormat.format(Calendar.getInstance().getTime());
		return str + RandomUtil.getRandomNumber(10);
	}

	/**
	 * @Title: 生成22位数的交易号
	 * @Description: TODO
	 * @return
	 * @author LILEI
	 * @date 2016年3月21日 下午2:55:58
	 * @version V1.0
	 */
	public static synchronized String get22OrderNumber() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = localSimpleDateFormat.format(Calendar.getInstance().getTime());
		return str + RandomUtil.getRandomNumber(5);
	}

	/**
	 * @Title: 生成18位数的交易号
	 * @Description: TODO
	 * @return
	 * @author LILEI
	 * @date 2016年6月6日 下午7:07:21
	 * @version V1.0
	 */
	public static synchronized String get18OrderNumber() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
		String str = localSimpleDateFormat.format(Calendar.getInstance().getTime());
		return str + RandomUtil.getRandomNumber(3);
	}

}
