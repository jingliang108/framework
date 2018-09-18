package com.giraffe.framework.base.database.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解的作用在实体类的字段上，结合mongodb的只返回部分属性使用。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BasicField {
    String value() default "";
}
