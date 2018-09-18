package com.giraffe.framework.base.common.utils;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;
import com.giraffe.framework.base.database.annotations.BasicField;
import com.giraffe.framework.base.database.annotations.CombobxField;

/**
 * Created by liuyijun on 2016/6/6.
 * 获取对象的主要字段工具类，主要用于mongodb中返回部分字段
 */
public class BasicFieldUtil {

    public static String[] getComboboxFiled(Class clazz) {
        return getComboboxFiled(MyBeanUtils.instantiate(clazz));
    }

    public static String[] getComboboxFiled(Object object) {
        List<String> result = Lists.newArrayList();
        Field[] fields = ReflectUtil.getAllFields(object);
        for (Field field : fields) {
            CombobxField annotation = field.getAnnotation(CombobxField.class);
            if (EmptyUtil.isNotEmpty(annotation)) {
                result.add(field.getName());
            }
        }
        return EmptyUtil.isNotEmpty(result) ? (String[]) result.toArray(new String[result.size()]) : null;
    }


    public static String[] getPrimaryFiled(Class clazz, String... defalutFields) {
        return getPrimaryFiled(MyBeanUtils.instantiate(clazz), defalutFields);
    }

    public static String[] getPrimaryFiled(Object object, String... defalutFields) {
        List<String> result = Lists.newArrayList();
        Field[] fields = ReflectUtil.getAllFields(object);
        for (Field field : fields) {
            BasicField annotation = field.getAnnotation(BasicField.class);
            if (EmptyUtil.isNotEmpty(annotation)) {
                result.add(field.getName());
            }
        }
        return EmptyUtil.isNotEmpty(result) ? (String[]) result.toArray(new String[result.size()]) : defalutFields;
    }


}
