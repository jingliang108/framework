package com.tanjin.framework.base.database.domain.search;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tanjin.framework.base.common.utils.EmptyUtil;
import com.tanjin.framework.base.database.domain.common.SerializableModel;
import com.tanjin.framework.base.database.domain.page.PageSearch;

/**
 * 查询条件封装类，实体类的属性值都是等于的条件
 * Created by liuyijun on 2016/7/28.
 */
public class SearchCondition<T extends Serializable> extends SerializableModel {
    public SearchCondition() {
    }

    public SearchCondition(Class<T> entityClazz) {
        this.entityClazz = entityClazz;
    }

    private Class<?> entityClazz;

    public SearchCondition(T modelExample) {
        this.modelExample = modelExample;
        this.entityClazz = modelExample.getClass();
    }

    public SearchCondition(T modelExample, PageSearch pageSearch) {
        this.modelExample = modelExample;
        this.pageSearch = pageSearch;
        this.entityClazz = modelExample.getClass();
    }

    public SearchCondition(boolean onlyBasicField, T modelExample, PageSearch pageSearch) {
        this.onlyBasicField = onlyBasicField;
        this.modelExample = modelExample;
        this.pageSearch = pageSearch;
        this.entityClazz = modelExample.getClass();
    }

    /**
     * 是否只查询实体对象的基础字段
     */
    private boolean onlyBasicField;

    private T modelExample;

    private Integer top;

    private String[] selectColumns;

    /**
     * 排序条件，排序字段为KEY，方向为值，可以添加多个
     */
    private Map<String, String> orderByConditions;

    private List<RangeCondition> rangeConditions;

    private Map<String, String> likeConditions;

    private Map<String, String> startWithConditions;

    private Map<String, String> endWithConditions;

    private Map<String, List<Object>> inConditions;

    private Map<String, List<Object>> notInConditions;


    private PageSearch pageSearch;

    public T getModelExample() {
        return modelExample;
    }

    public SearchCondition<T> setModelExample(T modelExample) {
        this.modelExample = modelExample;
        this.entityClazz = modelExample.getClass();
        return this;
    }

    public Map<String, String> getOrderByConditions() {
        return orderByConditions;
    }

    public SearchCondition<T> setOrderByConditions(Map<String, String> orderByConditions) {
        this.orderByConditions = orderByConditions;
        return this;
    }

    public List<RangeCondition> getRangeConditions() {
        return rangeConditions;
    }

    public SearchCondition<T> setRangeConditions(List<RangeCondition> rangeConditions) {
        this.rangeConditions = rangeConditions;
        return this;
    }

    public PageSearch getPageSearch() {
        return pageSearch;
    }

    public SearchCondition<T> setPageSearch(PageSearch pageSearch) {
        this.pageSearch = pageSearch;
        return this;
    }


    public boolean isOnlyBasicField() {
        return onlyBasicField;
    }

    public SearchCondition<T> setOnlyBasicField(boolean onlyBasicField) {
        this.onlyBasicField = onlyBasicField;
        return this;
    }


    public Integer getTop() {
        return top;
    }

    public SearchCondition<T> setTop(Integer top) {
        this.top = top;
        return this;
    }

    public Map<String, String> getLikeConditions() {
        return likeConditions;
    }

    public SearchCondition<T> setLikeConditions(Map<String, String> likeConditions) {
        this.likeConditions = likeConditions;
        return this;
    }

    public Map<String, String> getStartWithConditions() {
        return startWithConditions;
    }

    public void setStartWithConditions(Map<String, String> startWithConditions) {
        this.startWithConditions = startWithConditions;
    }

    public Map<String, String> getEndWithConditions() {
        return endWithConditions;
    }

    public void setEndWithConditions(Map<String, String> endWithConditions) {
        this.endWithConditions = endWithConditions;
    }

    public Map<String, List<Object>> getInConditions() {
        return inConditions;
    }

    public void setInConditions(Map<String, List<Object>> inConditions) {
        this.inConditions = inConditions;
    }

    public Map<String, List<Object>> getNotInConditions() {
        return notInConditions;
    }

    public void setNotInConditions(Map<String, List<Object>> notInConditions) {
        this.notInConditions = notInConditions;
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    public SearchCondition<T> setSelectColumns(String... selectColumns) {
        if (EmptyUtil.isNotEmpty(selectColumns) && selectColumns.length > 0) {
            this.selectColumns = selectColumns;
        }
        return this;
    }

    public SearchCondition<T> buildLikeConditions(String field, String value) {
        if (EmptyUtil.isEmpty(likeConditions)) {
            this.likeConditions = Maps.newHashMap();
        }
        this.likeConditions.put(field, value);
        return this;
    }

    public SearchCondition<T> buildRangeConditions(RangeCondition rangeCondition) {
        if (EmptyUtil.isEmpty(rangeConditions)) {
            this.rangeConditions = Lists.newArrayList();
        }
        this.rangeConditions.add(rangeCondition);
        return this;
    }

    public SearchCondition<T> buildOrderByConditions(String field, String value) {
        if (EmptyUtil.isEmpty(orderByConditions)) {
            this.orderByConditions = Maps.newHashMap();
        }
        this.orderByConditions.put(field, value);
        return this;
    }

    public SearchCondition buildStartWithConditions(String field, String value) {
        if (EmptyUtil.isEmpty(startWithConditions)) {
            this.startWithConditions = Maps.newHashMap();
        }
        this.startWithConditions.put(field, value);
        return this;
    }


    public SearchCondition buildEndWithConditions(String field, String value) {
        if (EmptyUtil.isEmpty(endWithConditions)) {
            this.endWithConditions = Maps.newHashMap();
        }
        this.endWithConditions.put(field, value);
        return this;
    }

    public SearchCondition buildInConditions(String field, List<Object> values) {
        if (EmptyUtil.isEmpty(inConditions)) {
            this.inConditions = Maps.newHashMap();
        }
        this.inConditions.put(field, values);
        return this;
    }

    public SearchCondition buildNotInConditions(String field, List<Object> values) {
        if (EmptyUtil.isEmpty(notInConditions)) {
            this.notInConditions = Maps.newHashMap();
        }
        this.notInConditions.put(field, values);
        return this;
    }

    public Class<?> getEntityClazz() {
        return entityClazz;
    }

    public SearchCondition<T> setEntityClazz(Class<?> entityClazz) {
        this.entityClazz = entityClazz;
        return this;
    }
}
