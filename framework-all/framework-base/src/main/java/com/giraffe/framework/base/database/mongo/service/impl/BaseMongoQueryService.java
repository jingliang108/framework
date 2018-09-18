package com.giraffe.framework.base.database.mongo.service.impl;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.giraffe.framework.base.database.base.service.IBaseQueryService;
import com.giraffe.framework.base.database.domain.page.PageResult;
import com.giraffe.framework.base.database.domain.search.SearchCondition;
import com.giraffe.framework.base.database.mongo.BaseMongoDAO;


public class BaseMongoQueryService<T extends Serializable> implements IBaseQueryService<T> {

    @Autowired
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
    public T findOneByField(String field, Object value) {
        return this.baseMongoDAO.findOne(field, value);
    }

    @Override
    public List<T> findByField(String field, Object value) {
        Query<T> query = this.baseMongoDAO.createQuery().field(field).equal(value);
        return query.asList();
    }
}
