package com.tanjin.framework.base.lock.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要共享锁的注解
 * 
 * @author liuyijun
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLock {
	/**
	 * 默认的共享锁的路径
	 * 
	 * @return 2014年9月16日 liuyijun
	 */
	String value() default "/shareLocks";

	/**
	 * 默认是共享锁
	 * @return
	 */
	String LockCategory() default "shareLock";
	/**
	 * 个人锁的类型，默认是账户
	 * @return
	 */
	String type() default "account";
	/**
	 * 动态参数的位置
	 * @return
	 */
	String argIndex() default "0";
	
	/**
	 * 是否是基础类型
	 * @return
	 */
	boolean isBaseClass() default false;
	/**
	 * 动态参数的属性名
	 * @return
	 */
	String argFiledName() default "cusId";
}
