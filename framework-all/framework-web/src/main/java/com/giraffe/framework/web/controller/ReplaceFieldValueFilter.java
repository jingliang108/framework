package com.tanjin.framework.web.controller;

import java.lang.reflect.Field;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.tanjin.framework.base.common.utils.EmptyUtil;
import com.tanjin.framework.base.common.utils.ReflectUtil;

/**
 * 根据注解信息在JSON序列化时替换相应的属性值
 * 
 * @author LiuYiJun
 *
 */
public class ReplaceFieldValueFilter implements ValueFilter {

	public Object process(Object object, String name, Object value) {
		if (object instanceof Replaceable) {
			if (EmptyUtil.isNotEmpty(value)) {
				if (value instanceof String) {
					String strValue = (String) value;
					Field field = ReflectUtil.getFieldByFieldName(object, name);
					if (EmptyUtil.isNotEmpty(field)) {
						JSONReplaceField replaceField = field.getAnnotation(JSONReplaceField.class);
						if (EmptyUtil.isNotEmpty(replaceField)) {
							return this.getReplacedValue(strValue, replaceField.replace(), replaceField.prefix(),
									replaceField.suffix());
						}
					}
				}
			}
		}
		return value;
	}

	/**
	 * 替换值
	 * 
	 * @param source
	 *            字符串源
	 * @param replace
	 *            替换的字符串如******、#####等
	 * @param prefix
	 *            源字符串前面保留的位数
	 * @param suffix
	 *            源字符串后面保留的位数
	 * @return
	 */
	private String getReplacedValue(String source, String replace, int prefix, int suffix) {
		String start = "";
		String end = "";
		if (prefix > 0) {
			start = source.substring(0, prefix);
		}
		if (suffix > 0) {
			if (prefix + suffix < source.length()) {
				end = source.substring(source.length() - suffix);
			}
		}
		return start + replace + end;
	}
}
