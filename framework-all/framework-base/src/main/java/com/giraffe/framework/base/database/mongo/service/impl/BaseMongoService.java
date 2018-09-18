package com.giraffe.framework.base.database.mongo.service.impl;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.giraffe.framework.base.database.base.service.BaseServiceUtil;
import com.giraffe.framework.base.database.base.service.IBaseService;
import com.giraffe.framework.base.database.domain.page.PageResult;
import com.giraffe.framework.base.database.domain.returns.BaseResult;
import com.giraffe.framework.base.database.domain.search.SearchCondition;
import com.giraffe.framework.base.database.mongo.BaseMongoDAO;


public class BaseMongoService<T extends Serializable> implements IBaseService<T> {

    @Autowired(required = false)
    private BaseMongoDAO<T, ObjectId> baseMongoDAO;

    @Override
    public T findById(Object id, boolean onlyPrimaryField) {
        return this.baseMongoDAO.findById((String) id, onlyPrimaryField);
    }

    @Override
    public T findById(Object id, String... selectColumns) {
        return this.baseMongoDAO.findById((String) id, selectColumns);
    }

    @Override
    public List<T> findByCondition(SearchCondition<T> condition) {
        return this.baseMongoDAO.findByCondition(condition);
    }

    @Override
    public T findOneByCondition(SearchCondition<T> condition) {
        return this.baseMongoDAO.findOneByCondition(condition);
    }

    @Override
    public List<T> findByCombobx(SearchCondition<T> condition) {
        return this.baseMongoDAO.findByCombobx(condition);
    }

    @Override
    public long countByCondition(SearchCondition<T> condition) {
        return this.baseMongoDAO.countByCondition(condition);
    }

    @Override
    public PageResult<T> findByPage(SearchCondition<T> condition) {
        return this.baseMongoDAO.findByPage(condition);
    }

    @Override
    public boolean exists(SearchCondition<T> condition) {
        return this.baseMongoDAO.exists(condition);
    }

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

    @Override
    public T findOneByField(String field, Object value) {
        return this.baseMongoDAO.findOne(field, value);
    }

    @Override
    public List<T> findByField(String field, Object value) {
        Query<T> query = this.baseMongoDAO.createQuery().field(field).equal(value);
        return query.asList();
    }
}
