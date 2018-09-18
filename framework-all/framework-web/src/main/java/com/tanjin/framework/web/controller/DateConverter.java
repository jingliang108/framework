package com.tanjin.framework.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.tanjin.framework.base.common.utils.EmptyUtil;


/**
 * springmvc 字符串格式时间参数的转换类，将接收到的string类型的时间转换成Date类型
 *
 * @author LiuYiJun
 * @date 2015年7月16日
 */
public class DateConverter implements Converter<String, Date> {
    //@Override
    public Date convert(String source) {
        SimpleDateFormat dateFormat = null;
        if (EmptyUtil.isNotEmpty(source)) {
            if (source.trim().length() > 10) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
        }
        if (null != dateFormat) {
            dateFormat.setLenient(true);
        }

        try {
            if (null != dateFormat) {
                return dateFormat.parse(source);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
