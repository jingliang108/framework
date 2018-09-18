package com.tanjin.framework.base.common.utils;

import java.lang.reflect.Field;
import java.util.Date;

import org.mongodb.morphia.annotations.Transient;

import com.tanjin.framework.base.database.annotations.DefaultValueOnSave;
import com.tanjin.framework.base.database.annotations.DefaultValueType;
import com.tanjin.framework.base.database.domain.common.ObjectId;

/**
 * 根据DefaultValue注解在进行新增记录时给实体对象赋默认值
 */
public class ModelSetFieldDefaultValueUtil {

    public static void setFieldDefaultValue(Object entity) {
        if (EmptyUtil.isNotEmpty(entity)) {
            Field[] fields = ReflectUtil.getAllFieldsByCutOff(entity, Object.class);
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!"serialVersionUID".equals(fieldName)) {
                    Transient tr = field.getAnnotation(Transient.class);
                    if (EmptyUtil.isEmpty(tr)) {
                        DefaultValueOnSave defaultValue = field.getAnnotation(DefaultValueOnSave.class);
                        if (EmptyUtil.isNotEmpty(defaultValue)) {
                            Object value = null;
                            try {
                                value = ReflectUtil.getValueByFieldName(entity, fieldName);
                                if (EmptyUtil.isEmpty(value)) {
                                    DefaultValueType defaultValueType = defaultValue.type();
                                    switch (defaultValueType) {
                                        case STRING:
                                            value = defaultValue.strValue();
                                            break;
                                        case INTEGER:
                                            value = defaultValue.intValue();
                                            break;
                                        case BOOLEAN:
                                            value = defaultValue.booValue();
                                            break;
                                        case UUID:
                                            value = ObjectId.get();
                                            break;
                                        case DATE:
                                            value = new Date();
                                            break;
                                    }
                                    ReflectUtil.setValueByFieldName(entity, fieldName, value);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
