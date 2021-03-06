package com.tanjin.framework.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不需要session检查
 * 
 * @author LiuYiJun
 * @date 2015年7月22日
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnSession {
	String value() default "";
}
