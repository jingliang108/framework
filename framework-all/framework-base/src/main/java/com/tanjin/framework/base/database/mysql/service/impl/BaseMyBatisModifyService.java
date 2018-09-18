package com.tanjin.framework.base.database.mysql.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.tanjin.framework.base.common.utils.ModelSetFieldDefaultValueUtil;
import com.tanjin.framework.base.database.base.service.BaseServiceUtil;
import com.tanjin.framework.base.database.base.service.ExampleUtil;
import com.tanjin.framework.base.database.base.service.IBaseModifyService;
import com.tanjin.framework.base.database.domain.returns.BaseResult;
import com.tanjin.framework.base.database.domain.search.SearchCondition;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class BaseMyBatisModifyService<T extends Serializable> implements IBaseModifyService<T> {

    @Autowired(required = false)
    protected Mapper<T> mapper;


    @Override
    public BaseResult<String> save(T entity) {
        ModelSetFieldDefaultValueUtil.setFieldDefaultValue(entity);
        Integer successNum = MyBatisServiceUtil.saveSelective(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum, entity);
    }

    @Override
    public BaseResult<Integer> removeById(Object id) {
        Integer successNum = MyBatisServiceUtil.removeByPrimaryKey(id, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> remove(T entity) {
        Integer successNum = MyBatisServiceUtil.remove(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> removeByCondition(SearchCondition<T> condition) {
        Example example = ExampleUtil.getBaseExampleBySearchCondition(condition);
        Integer successNum = MyBatisServiceUtil.removeByExample(example, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> modifyEntity(T entity) {
        Integer successNum = MyBatisServiceUtil.modifyByPrimaryKeySelective(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> modifyEntityByCondition(T newEntity, SearchCondition<T> condition) {
        Example example = ExampleUtil.getBaseExampleBySearchCondition(condition);
        Integer successNum = MyBatisServiceUtil.modifyByExampleSelective(newEntity, example, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }
}
