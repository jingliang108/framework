package com.giraffe.framework.base.database.domain.search;

import com.giraffe.framework.base.database.domain.common.SerializableModel;

/**
 * 时间条件封装类，如果只有一个日期条件，请设置在startDate中
 */
public class RangeCondition extends SerializableModel {

    /**
     * 如果只有一个日期条件，请设置在startDate中
     *
     * @param field
     * @param startValue
     * @param type
     */
    public RangeCondition(String field, Object startValue, RangeConditionType type) {
        this.field = field;
        this.startValue = startValue;
        this.type = type;
    }

    /**
     * 如果同时设置了startDate 和 endDate ,默认就是between类型的条件
     *
     * @param field
     * @param startValue
     * @param endValue
     */
    public RangeCondition(String field, Object startValue, Object endValue) {
        this.field = field;
        this.startValue = startValue;
        this.endValue = endValue;
        this.type = RangeConditionType.Between;
    }

    private String field;

    private Object startValue;

    private Object endValue;

    private RangeConditionType type;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getStartValue() {
        return startValue;
    }

    public void setStartValue(Object startValue) {
        this.startValue = startValue;
    }

    public Object getEndValue() {
        return endValue;
    }

    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }

    public RangeConditionType getType() {
        return type;
    }

    public void setType(RangeConditionType type) {
        this.type = type;
    }
}
