package com.tanjin.framework.base.database.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置实体对象的属性的默认值
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DefaultValueOnSave {
    String value() default "";

    String strValue() default "";

    int intValue() default 0;

    DefaultValueType type() default DefaultValueType.STRING;

    boolean booValue() default false;
}
