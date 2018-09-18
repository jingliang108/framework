package com.giraffe.framework.base.common.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	/**
	 * @Title: 日期时间类型转成日期
	 * @Description: YYYY_MM_DD_HH_MM_SS 变成 YYYY_MM_DD
	 * @param date
	 * @return
	 * @author LILEI
	 * @date 2016年3月26日 下午3:21:25
	 * @version V1.0
	 */
	public static Date dateTimeToDate(Date date) {
		return DateUtil.StringToDate(DateUtil.getDate(date), DateStyle.YYYY_MM_DD);
	}

	/**
	 * @Title: 日期时间类型转成时间
	 * @Description: YYYY_MM_DD_HH_MM_SS 变成 HH_MM_SS
	 * @param date
	 * @return
	 * @author LILEI
	 * @date 2016年3月28日 下午7:00:06
	 * @version V1.0
	 */
	public static Date dateTimeToTime(Date date) {
		return DateUtil.StringToDate(DateUtil.getTime(date), DateStyle.HH_MM_SS);
	}

	/**
	 * 获取与指定日期相差指定 小时 的日期
	 *
	 * @param baseDate
	 *            日期
	 * @param hourCount
	 *            向前或向后的小时，向后为正数，向前为负数
	 * @return 日期
	 */
	public static Date getAfterHourAsDate(Date baseDate, int hourCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.HOUR, hourCount);
		return calendar.getTime();
	}

	/**
	 * 检测d1 是否大于等于d2
	 *
	 * @param d1
	 * @param d2
	 * @return true d1 是否大于等于d2
	 */
	public static boolean checkMax(Date d1, Date d2) {
		boolean flag = false;
		if (null != d1) {
			if (null != d2) {
				String d1s = getDateString(d1, "yyyyMMdd");
				String d12s = getDateString(d2, "yyyyMMdd");
				if (Double.valueOf(d1s) >= Double.valueOf(d12s)) {
					flag = true;
				}
			} else {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 检测d1 是否小于d2 只比较日期
	 *
	 * @param d1
	 * @param d2
	 * @return true d1 是否小于d2
	 */
	public static boolean checkLess(Date d1, Date d2) {
		boolean flag = false;
		if (null != d1) {
			if (null != d2) {
				String d1s = getDateString(d1, "yyyyMMdd");
				String d12s = getDateString(d2, "yyyyMMdd");
				if (Double.valueOf(d1s) < Double.valueOf(d12s)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 检测d1 是否小于d2 比较时间
	 *
	 * @author LiuYiJun
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static boolean checkLessTime(String dateStr1, String dateStr2) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(dateStr1));
			c2.setTime(df.parse(dateStr2));
		} catch (java.text.ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		if (result < 0) {
			return true;
		}

		return false;
	}

	/**
	 * 检测d1 是否小于d2 比较时间
	 *
	 * @author LiuYiJun
	 * @date 2015年7月17日
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean checkLessTime(Date date1, Date date2) {
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int result = c1.compareTo(c2);
		if (result < 0) {
			return true;
		}
		return false;
	}

	/**
	 * 检测d1 是否小于等于d2 比较时间
	 *
	 * @author jingliang
	 * @date 2015年7月17日
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean checkLessEqualsTime(Date date1, Date date2) {
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int result = c1.compareTo(c2);
		if (result <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 日期格式化(Date转换为String)
	 *
	 * @param _date
	 *            日期
	 * @param patternString
	 *            处理结果日期的显示格式，如："YYYY-MM-dd"
	 * @return
	 */
	public static String getDateString(Date _date, String patternString) {
		String dateString = "";
		if (_date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(patternString);
			dateString = formatter.format(_date);
		}
		return dateString;
	}

	/**
	 * 获取SimpleDateFormat
	 *
	 * @param parttern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
		return new SimpleDateFormat(parttern);
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *            日期字符串
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期字符串
	 */
	private static String addInteger(String date, int dateType, int amount) {
		String dateString = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = DateToString(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (dateType == Calendar.MONTH) {
			return calendar.get(dateType) + 1;
		}
		return calendar.get(dateType);
	}

	/**
	 * 判断字符串是否为日期字符串
	 *
	 * @param date
	 *            日期字符串
	 * @return true or false
	 */
	public static boolean isDate(String date) {
		boolean isDate = false;
		if (date != null) {
			if (StringToDate(date) != null) {
				isDate = true;
			}
		}
		return isDate;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date StringToDate(String date) {
		DateStyle dateStyle = null;
		return StringToDate(date, dateStyle);
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param parttern
	 *            日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String parttern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormat(parttern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param dateStyle
	 *            日期风格
	 * @return 日期
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle == null) {
			List<Long> timestamps = new ArrayList<Long>();
			for (DateStyle style : DateStyle.values()) {
				Date dateTmp = StringToDate(date, style.getValue());
				if (dateTmp != null) {
					timestamps.add(dateTmp.getTime());
				}
			}
			myDate = getAccurateDate(timestamps);
		} else {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param parttern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String parttern) {
		String dateString = null;
		if (date != null) {
			try {
				dateString = getDateFormat(parttern).format(date);
			} catch (Exception e) {
			}
		}
		return dateString;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param dateStyle
	 *            日期风格
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, DateStyle dateStyle) {
		String dateString = null;
		if (dateStyle != null) {
			dateString = DateToString(date, dateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param parttern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String parttern) {
		return StringToString(date, null, parttern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param dateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle dateStyle) {
		return StringToString(date, null, dateStyle);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param olddParttern
	 *            旧日期格式
	 * @param newParttern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String olddParttern, String newParttern) {
		String dateString = null;
		if (olddParttern == null) {
			DateStyle style = getDateStyle(date);
			if (style != null) {
				Date myDate = StringToDate(date, style.getValue());
				dateString = DateToString(myDate, newParttern);
			}
		} else {
			Date myDate = StringToDate(date, olddParttern);
			dateString = DateToString(myDate, newParttern);
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param olddDteStyle
	 *            旧日期风格
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
		String dateString = null;
		if (olddDteStyle == null) {
			DateStyle style = getDateStyle(date);
			dateString = StringToString(date, style.getValue(), newDateStyle.getValue());
		} else {
			dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * @Title: 事例
	 * @Description: TODO
	 * @param args
	 * @author LILEI
	 * @date 2015年12月15日 下午3:39:20
	 * @version V1.0
	 */
	public static void main(String[] args) {
		// TODO 123
		// 时间加减
		System.out.println(DateUtil.addInteger("2015-12-15 12:00:00", Calendar.DAY_OF_MONTH, -1));
		System.out.println(DateUtil.addInteger(new Date(), Calendar.DAY_OF_MONTH, -1));
		// string转date类型
		System.out.println(DateUtil.StringToDate("2015-12-15 12:12:12", DateStyle.YYYY_MM_DD));
		// date转string类型
		System.out.println(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		// 获取日期
		System.out.println(DateUtil.getDate(new Date()));
		// 获取时间
		System.out.println(DateUtil.getTime(new Date()));

	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(String date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static String addMonth(String date, int yearAmount) {
		return addInteger(date, Calendar.MONTH, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int yearAmount) {
		return addInteger(date, Calendar.MONTH, yearAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(String date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(String date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(String date, int hourAmount) {
		return addInteger(date, Calendar.MINUTE, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(Date date, int hourAmount) {
		return addInteger(date, Calendar.MINUTE, hourAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期字符串
	 */
	public static String addSecond(String date, int hourAmount) {
		return addInteger(date, Calendar.SECOND, hourAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(Date date, int hourAmount) {
		return addInteger(date, Calendar.SECOND, hourAmount);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 年份
	 */
	public static int getYear(String date) {
		return getYear(StringToDate(date));
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 月份
	 */
	public static int getMonth(String date) {
		return getMonth(StringToDate(date));
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH);
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 天
	 */
	public static int getDay(String date) {
		return getDay(StringToDate(date));
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 小时
	 */
	public static int getHour(String date) {
		return getHour(StringToDate(date));
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 分钟
	 */
	public static int getMinute(String date) {
		return getMinute(StringToDate(date));
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 秒钟
	 */
	public static int getSecond(String date) {
		return getSecond(StringToDate(date));
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 秒钟
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static String getDate(String date) {
		return StringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 日期
	 */
	public static String getDateTime(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 日期
	 */
	public static String getDate(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 时间
	 */
	public static String getTime(String date) {
		return StringToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 时间
	 */
	public static String getTime(Date date) {
		return DateToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 星期
	 */
	public static DateWeek getDateWeek(String date) {
		DateWeek DateWeek = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			DateWeek = getDateWeek(myDate);
		}
		return DateWeek;
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 星期
	 */
	public static DateWeek getDateWeek(Date date) {
		DateWeek DateWeek = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int DateWeekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (DateWeekNumber) {
		case 0:
			DateWeek = DateWeek.SUNDAY;
			break;
		case 1:
			DateWeek = DateWeek.MONDAY;
			break;
		case 2:
			DateWeek = DateWeek.TUESDAY;
			break;
		case 3:
			DateWeek = DateWeek.WEDNESDAY;
			break;
		case 4:
			DateWeek = DateWeek.THURSDAY;
			break;
		case 5:
			DateWeek = DateWeek.FRIDAY;
			break;
		case 6:
			DateWeek = DateWeek.SATURDAY;
			break;
		}
		return DateWeek;
	}

	/**
	 * 获取两个日期相差的天数
	 *
	 * @param date
	 *            日期字符串
	 * @param otherDate
	 *            另一个日期字符串
	 * @return 相差天数
	 */
	public static int getIntervalDays(String date, String otherDate) {
		return getIntervalDays(StringToDate(date), StringToDate(otherDate));
	}

	/**
	 * @param date
	 *            日期
	 * @param otherDate
	 *            另一个日期
	 * @return 相差天数
	 */
	public static int getIntervalDays(Date date, Date otherDate) {
		date = DateUtil.StringToDate(DateUtil.getDate(date));
		long time = Math.abs(date.getTime() - otherDate.getTime());
		return (int) time / (24 * 60 * 60 * 1000);
	}

	/**
	 * @param date
	 *            时间
	 * @param otherDate
	 *            另一个时间
	 * @return 相差秒
	 */
	public static int getIntervalSecond(Date dateTime, Date otherDateTime) {
		dateTime = StringToDate(DateToString(dateTime, "yyyy-MM-dd HH:mm:ss"), DateStyle.YYYY_MM_DD_HH_MM_SS);
		otherDateTime = StringToDate(DateToString(otherDateTime, "yyyy-MM-dd HH:mm:ss"), DateStyle.YYYY_MM_DD_HH_MM_SS);
		long time = otherDateTime.getTime() - dateTime.getTime();
		return (int) time / (1000);
	}

	/**
	 * 获取精确的日期
	 *
	 * @param timestamps
	 *            时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(List<Long> timestamps) {
		Date date = null;
		long timestamp = 0;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		List<Long> absoluteValues = new ArrayList<Long>();

		if (timestamps != null && timestamps.size() > 0) {
			if (timestamps.size() > 1) {
				for (int i = 0; i < timestamps.size(); i++) {
					for (int j = i + 1; j < timestamps.size(); j++) {
						long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
						absoluteValues.add(absoluteValue);
						long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
						map.put(absoluteValue, timestampTmp);
					}
				}

				// 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
				long minAbsoluteValue = -1;
				if (!absoluteValues.isEmpty()) {
					// 如果timestamps的size为2，这是差值只有一个，因此要给默认值
					minAbsoluteValue = absoluteValues.get(0);
				}
				for (int i = 0; i < absoluteValues.size(); i++) {
					for (int j = i + 1; j < absoluteValues.size(); j++) {
						if (absoluteValues.get(i) > absoluteValues.get(j)) {
							minAbsoluteValue = absoluteValues.get(j);
						} else {
							minAbsoluteValue = absoluteValues.get(i);
						}
					}
				}

				if (minAbsoluteValue != -1) {
					long[] timestampsLastTmp = map.get(minAbsoluteValue);
					if (absoluteValues.size() > 1) {
						timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
					} else if (absoluteValues.size() == 1) {
						// 当timestamps的size为2，需要与当前时间作为参照
						long dateOne = timestampsLastTmp[0];
						long dateTwo = timestampsLastTmp[1];
						if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
							timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
						} else {
							long now = new Date().getTime();
							if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
								timestamp = dateOne;
							} else {
								timestamp = dateTwo;
							}
						}
					}
				}
			} else {
				timestamp = timestamps.get(0);
			}
		}

		if (timestamp != 0) {
			date = new Date(timestamp);
		}
		return date;
	}

	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(String date) {
		DateStyle dateStyle = null;
		Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		List<Long> timestamps = new ArrayList<Long>();
		for (DateStyle style : DateStyle.values()) {
			Date dateTmp = StringToDate(date, style.getValue());
			if (dateTmp != null) {
				timestamps.add(dateTmp.getTime());
				map.put(dateTmp.getTime(), style);
			}
		}
		dateStyle = map.get(getAccurateDate(timestamps).getTime());
		return dateStyle;
	}

}
