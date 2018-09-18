package com.giraffe.framework.base.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射的Utils函数集合.扩展自spring BeanUtils
 */
public class MyBeanUtils extends BeanUtils {

    private static Objenesis objenesis = new ObjenesisStd(true);

    /**
     * 只拷贝值不为NULL的属性值
     *
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyPropertiesNotNull(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 从一个对象中拷贝属性值，该属性在两个对象中名称相同且类型也相同
     *
     * @param ori 被拷贝的对象
     * @param cls 拷贝对象的类型
     * @return 拷贝对象的一个实例对象
     */
    public static <T> T copyBean(Object ori, Class<T> cls) {
        T obj = (T) objenesis.newInstance(cls);
        copyProperties(ori, obj);
        return obj;
    }

}
