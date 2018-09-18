package com.giraffe.framework.base.database.mongo.service.impl;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.giraffe.framework.base.database.base.service.BaseServiceUtil;
import com.giraffe.framework.base.database.base.service.IBaseModifyService;
import com.giraffe.framework.base.database.domain.returns.BaseResult;
import com.giraffe.framework.base.database.domain.search.SearchCondition;
import com.giraffe.framework.base.database.mongo.BaseMongoDAO;


public class BaseMongoModifyService<T extends Serializable> implements IBaseModifyService<T> {

    @Autowired
    private BaseMongoDAO<T, ObjectId> baseMongoDAO;

    @Override
    public BaseResult<String> save(T entity) {
        String id = this.baseMongoDAO.save(entity).getId().toString();
        return new BaseResult<String>(id);
    }

    @Override
    public BaseResult<Integer> removeById(Object id) {
        String strId = (String) id;
        Integer successNum = this.baseMongoDAO.deleteById(new ObjectId(strId)).getN();
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> remove(T entity) {
        Integer successNum = this.baseMongoDAO.delete(entity).getN();
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> removeByCondition(SearchCondition<T> condition) {
        Integer successNum = this.baseMongoDAO.deleteByConditon(condition).getN();
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> modifyEntity(T entity) {
        Integer successNum = this.baseMongoDAO.updateEntity(entity);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult<Integer> modifyEntityByCondition(T newEntity, SearchCondition<T> condition) {
        Integer successNum = this.baseMongoDAO.updateEntityByCondition(newEntity, condition);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }
}
