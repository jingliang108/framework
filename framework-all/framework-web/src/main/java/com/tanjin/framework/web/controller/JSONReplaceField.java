package com.tanjin.framework.web.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在JSON序列化时替换属性值得注解
 * 
 * @author LiuYiJun
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface JSONReplaceField {

	String value() default "";

	/**
	 * 源字符串前面保留的位数
	 * 
	 * @return
	 */
	int prefix() default 0;

	/**
	 * 源字符串后面保留的位数
	 * 
	 * @return
	 */
	int suffix() default 0;

	/**
	 * 替换的字符串如******、#####等
	 * 
	 * @return
	 */
	String replace() default "******";

}
