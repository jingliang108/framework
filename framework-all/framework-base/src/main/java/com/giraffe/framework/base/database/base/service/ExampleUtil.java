package com.giraffe.framework.base.database.base.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.giraffe.framework.base.common.utils.BasicFieldUtil;
import com.giraffe.framework.base.common.utils.EmptyUtil;
import com.giraffe.framework.base.common.utils.ReflectUtil;
import com.giraffe.framework.base.database.domain.search.RangeCondition;
import com.giraffe.framework.base.database.domain.search.RangeConditionType;
import com.giraffe.framework.base.database.domain.search.SearchCondition;

import tk.mybatis.mapper.entity.Example;


public class ExampleUtil {


    public static Example createFindByFieldExampleByClass(Class<?> clazz, String field, Object value) {
        Example example = new Example(clazz);
        example.createCriteria().andEqualTo(field, value);
        return example;
    }


    public static Example createFindByIdAndSelectColumnsExample(Class<?> clazz, Object id, String[] fields) {
        Example example = new Example(clazz);
        String fieldName = ReflectUtil.getFieldNameByAnnotation(clazz, Id.class);
        example.createCriteria().andEqualTo(fieldName, id);
        if (EmptyUtil.isNotEmpty(fields) && fields.length > 0) {
            example.selectProperties(fields);
        }
        return example;
    }

    public static <T extends Serializable> Example getExampleBySearchCondition(SearchCondition<T> condition, boolean isCombobox) {
        Example example = getBaseExampleBySearchCondition(condition);
        //排序处理
        doOrderByCondition(example, condition.getOrderByConditions());
        //筛选字段
        doSelectColumns(condition, example, isCombobox);
        return example;
    }

    public static <T extends Serializable> Example getBaseExampleBySearchCondition(SearchCondition<T> condition) {
        Example example = new Example(condition.getEntityClazz());
        T exampleEntity = condition.getModelExample();
        if (EmptyUtil.isNotEmpty(exampleEntity)) {
            Example.Criteria criteria = example.createCriteria();
            doEntityCondition(criteria, exampleEntity);
            //区间条件处理
            doRangeCondition(criteria, condition.getRangeConditions());
            //模糊查询处理
            doLikeCondition(criteria, condition.getLikeConditions());

            //模糊查询处理（以xx开头）
            doStartWithCondition(criteria, condition.getStartWithConditions());

            //模糊查询处理（以xx结尾）
            doEndWithCondition(criteria, condition.getEndWithConditions());

            //In查询处理
            doInCondition(criteria, condition.getInConditions());
            //NotIn查询处理
            doNotInCondition(criteria,condition.getNotInConditions());

        }
        return example;
    }


    private static <T extends Serializable> void doEntityCondition(Example.Criteria criteria, T exampleEntity) {
        Field[] fields = ReflectUtil.getAllFields(exampleEntity);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!"serialVersionUID".equals(fieldName)) {
                Transient tr = field.getAnnotation(Transient.class);
                if (EmptyUtil.isEmpty(tr)) {
                    Object value;
                    try {
                        value = ReflectUtil.getValueByFieldName(exampleEntity, fieldName);
                        if (EmptyUtil.isNotEmpty(value)) {
                            if (field.getType() != Date.class) {
                                criteria.andEqualTo(fieldName, value);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void doRangeCondition(Example.Criteria criteria, List<RangeCondition> rangeConditions) {
        if (EmptyUtil.isNotEmpty(rangeConditions) && rangeConditions.size() > 0) {
            for (RangeCondition dateCondition : rangeConditions) {
                if (EmptyUtil.isNotEmpty(dateCondition.getStartValue()) || EmptyUtil.isNotEmpty(dateCondition.getEndValue())) {
                    if (EmptyUtil.isNotEmpty(dateCondition.getType())) {
                        RangeConditionType dateConditionType = dateCondition.getType();
                        switch (dateConditionType) {
                            case GreaterThan:
                                criteria.andGreaterThan(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case GreaterThanOrEqual:
                                criteria.andGreaterThanOrEqualTo(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case LessThan:
                                criteria.andLessThan(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case LessThanOrEqual:
                                criteria.andLessThanOrEqualTo(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case Equal:
                                criteria.andEqualTo(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case Between:
                                if (EmptyUtil.isNotEmpty(dateCondition.getEndValue()) && EmptyUtil.isNotEmpty(dateCondition.getStartValue())) {
                                    criteria.andBetween(dateCondition.getField(), dateCondition.getStartValue(), dateCondition.getEndValue());
                                } else if (EmptyUtil.isNotEmpty(dateCondition.getEndValue()) && EmptyUtil.isEmpty(dateCondition.getStartValue())) {
                                    criteria.andLessThanOrEqualTo(dateCondition.getField(), dateCondition.getEndValue());
                                } else if (EmptyUtil.isEmpty(dateCondition.getEndValue()) && EmptyUtil.isNotEmpty(dateCondition.getStartValue())) {
                                    criteria.andGreaterThanOrEqualTo(dateCondition.getField(), dateCondition.getEndValue());
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private static void doOrderByCondition(Example example, Map<String, String> orderByCondition) {
        if (EmptyUtil.isNotEmpty(orderByCondition)) {
            for (String key : orderByCondition.keySet()) {
                if ("desc".equals(orderByCondition.get(key))) {
                    example.orderBy(key).desc();
                } else {
                    example.orderBy(key).asc();
                }
            }

        }
    }


    private static void doLikeCondition(Example.Criteria criteria, Map<String, String> likeCondition) {
        if (EmptyUtil.isNotEmpty(likeCondition)) {
            for (String key : likeCondition.keySet()) {
                criteria.andLike(key, likeCondition.get(key));
            }
        }
    }


    private static void doStartWithCondition(Example.Criteria criteria, Map<String, String> startWithCondition) {
        if (EmptyUtil.isNotEmpty(startWithCondition)) {
            for (String key : startWithCondition.keySet()) {
                criteria.andLike(key, startWithCondition.get(key) + "%");
            }
        }
    }

    private static void doEndWithCondition(Example.Criteria criteria, Map<String, String> endWithCondition) {
        if (EmptyUtil.isNotEmpty(endWithCondition)) {
            for (String key : endWithCondition.keySet()) {
                criteria.andLike(key, "%" + endWithCondition.get(key));
            }
        }
    }

    private static void doInCondition(Example.Criteria criteria, Map<String, List<Object>> inCondition) {
        if (EmptyUtil.isNotEmpty(inCondition)) {
            for (String key : inCondition.keySet()) {
                criteria.andIn(key, inCondition.get(key));
            }
        }
    }

    private static void doNotInCondition(Example.Criteria criteria, Map<String, List<Object>> notInCondition) {
        if (EmptyUtil.isNotEmpty(notInCondition)) {
            for (String key : notInCondition.keySet()) {
                criteria.andNotIn(key, notInCondition.get(key));
            }
        }
    }


    private static <T extends Serializable> void doSelectColumns(SearchCondition<T> condition, Example example, boolean isCombobox) {
        if (EmptyUtil.isNotEmpty(condition.getSelectColumns()) && condition.getSelectColumns().length > 0) {
            example.selectProperties(condition.getSelectColumns());
        } else {
            String[] fields = null;
            if (isCombobox) {
                fields = BasicFieldUtil.getComboboxFiled(condition.getEntityClazz());
            } else {
                if (condition.isOnlyBasicField()) {
                    fields = BasicFieldUtil.getPrimaryFiled(condition.getEntityClazz());
                }
            }
            if (EmptyUtil.isNotEmpty(fields) && fields.length > 0) {
                example.selectProperties(fields);
            }
        }

    }


}
