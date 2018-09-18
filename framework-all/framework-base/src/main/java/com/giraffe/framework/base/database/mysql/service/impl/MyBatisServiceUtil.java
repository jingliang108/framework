package com.giraffe.framework.base.database.mysql.service.impl;


import java.util.List;

import com.github.pagehelper.PageHelper;
import com.giraffe.framework.base.database.domain.page.PageResult;
import com.giraffe.framework.base.database.domain.page.PageSearch;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class MyBatisServiceUtil {


    public static <T> long countByExample(Example example, Mapper<T> mapper) {
        return mapper.selectCountByExample(example);
    }


    public static <T> int save(T entity, Mapper<T> mapper) {
        return mapper.insert(entity);
    }


    public static <T> int saveSelective(T entity, Mapper<T> mapper) {
        return mapper.insertSelective(entity);
    }


    public static <T> int remove(T entity, Mapper<T> mapper) {
        return mapper.delete(entity);
    }


    public static <T> int removeByPrimaryKey(Object primaryKey, Mapper<T> mapper) {
        return mapper.deleteByPrimaryKey(primaryKey);
    }


    public static <T> int removeByExample(Example example, Mapper<T> mapper) {
        return mapper.deleteByExample(example);
    }


    public static <T> List<T> findByExample(Example example, Mapper<T> mapper) {
        return mapper.selectByExample(example);
    }


    public static <T> int modifyByExampleSelective(T entity, Example example, Mapper<T> mapper) {
        return mapper.updateByExampleSelective(entity, example);
    }


    public static <T> int modifyByExample(T entity, Example example, Mapper<T> mapper) {
        return mapper.updateByExample(entity, example);
    }


    public static <T> int modifyByPrimaryKeySelective(T entity, Mapper<T> mapper) {
        return mapper.updateByPrimaryKeySelective(entity);
    }


    public static <T> int modifyByPrimaryKey(T entity, Mapper<T> mapper) {
        return mapper.updateByPrimaryKey(entity);
    }


    public static <T> PageResult<T> findByPage(PageSearch search, Example example, Mapper<T> mapper) {
        PageResult<T> result = new PageResult<T>();
        PageHelper.startPage(search.getPage(), search.getRows());
        List<T> list = mapper.selectByExample(example);
        int count = mapper.selectCountByExample(example);
        result.setRows(list);
        result.setTotal(count);
        return result;
    }



}
