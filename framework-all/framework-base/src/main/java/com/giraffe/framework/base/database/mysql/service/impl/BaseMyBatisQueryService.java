package com.giraffe.framework.base.database.mysql.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.giraffe.framework.base.common.utils.BasicFieldUtil;
import com.giraffe.framework.base.common.utils.EmptyUtil;
import com.giraffe.framework.base.database.base.service.ExampleUtil;
import com.giraffe.framework.base.database.base.service.IBaseQueryService;
import com.giraffe.framework.base.database.domain.page.PageResult;
import com.giraffe.framework.base.database.domain.page.PageSearch;
import com.giraffe.framework.base.database.domain.search.SearchCondition;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class BaseMyBatisQueryService<T extends Serializable> implements IBaseQueryService<T> {

	@Autowired(required = false)
	protected Mapper<T> mapper;

	protected Class<T> entityClazz;

	@Override
	public T findById(Object id, String... selectColumns) {
		if (EmptyUtil.isNotEmpty(selectColumns) && selectColumns.length > 0) {
			Example example = ExampleUtil.createFindByIdAndSelectColumnsExample(entityClazz, id, selectColumns);
			return mapper.selectByExample(example).get(0);
		}
		return this.mapper.selectByPrimaryKey(id);

	}

	@Override
	public List<T> findByCondition(SearchCondition<T> condition) {
		Example example = ExampleUtil.getExampleBySearchCondition(condition, false);
		if (EmptyUtil.isNotEmpty(condition.getTop())) {
			PageHelper.startPage(0, condition.getTop());
		}
		return MyBatisServiceUtil.findByExample(example, mapper);
	}

	@Override
	public T findOneByCondition(SearchCondition<T> condition) {
		Example example = ExampleUtil.getExampleBySearchCondition(condition, false);
		List<T> list = MyBatisServiceUtil.findByExample(example, mapper);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public List<T> findByCombobx(SearchCondition<T> condition) {
		Example example = ExampleUtil.getExampleBySearchCondition(condition, true);
		if (EmptyUtil.isNotEmpty(condition.getTop())) {
			PageHelper.startPage(0, condition.getTop());
		}
		return MyBatisServiceUtil.findByExample(example, mapper);
	}

	@Override
	public long countByCondition(SearchCondition<T> condition) {
		Example example = ExampleUtil.getBaseExampleBySearchCondition(condition);
		return MyBatisServiceUtil.countByExample(example, mapper);
	}

	@Override
	public PageResult<T> findByPage(SearchCondition<T> condition) {
		if (EmptyUtil.isEmpty(condition.getPageSearch())) {
			condition.setPageSearch(new PageSearch());
		}
		Example example = ExampleUtil.getExampleBySearchCondition(condition, false);
		return MyBatisServiceUtil.findByPage(condition.getPageSearch(), example, mapper);
	}

	@Override
	public boolean exists(SearchCondition<T> condition) {
		Example example = ExampleUtil.getBaseExampleBySearchCondition(condition);
		return MyBatisServiceUtil.countByExample(example, mapper) > 0;
	}

	@Override
	public T findById(Object id, boolean onlyPrimaryField) {
		if (onlyPrimaryField) {
			String[] fields = BasicFieldUtil.getPrimaryFiled(this.entityClazz);
			Example example = ExampleUtil.createFindByIdAndSelectColumnsExample(entityClazz, id, fields);
			return mapper.selectByExample(example).get(0);
		}
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public T findOneByField(String field, Object value) {
		List<T> list = this.findByField(field, value);
		if (EmptyUtil.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<T> findByField(String field, Object value) {
		Example example = ExampleUtil.createFindByFieldExampleByClass(this.entityClazz, field, value);
		return mapper.selectByExample(example);
	}

	public Class<T> getEntityClazz() {
		return entityClazz;
	}

	public void setEntityClazz(Class<T> entityClazz) {
		this.entityClazz = entityClazz;
	}

}
