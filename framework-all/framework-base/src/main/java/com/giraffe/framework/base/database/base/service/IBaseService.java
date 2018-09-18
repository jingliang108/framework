package com.tanjin.framework.base.database.base.service;

import java.io.Serializable;
import java.util.List;

import com.tanjin.framework.base.database.domain.page.PageResult;
import com.tanjin.framework.base.database.domain.returns.BaseResult;
import com.tanjin.framework.base.database.domain.search.SearchCondition;

import tk.mybatis.mapper.common.Mapper;


public interface IBaseService<T extends Serializable> {

    /**
     * 根据Id值查询
     *
     * @param id
     * @param onlyPrimaryField 是否只返回实体类的主要属性
     * @return 查询到的唯一实体对象
     */
    T findById(Object id, boolean onlyPrimaryField);

    /**
     * 根据Id值查询
     *
     * @param id id
     * @param selectColumns 自定义字段
     * @return 查询到的唯一实体对象
     */
    T findById(Object id, String... selectColumns);

    /**
     * 据条件查询数据
     *
     * @param condition 条件
     * @return 查询到的集合列表
     */
    List<T> findByCondition(SearchCondition<T> condition);

    /**
     * 据条件查询数据
     *
     * @param condition 条件
     * @return 查询到的集合列表
     */
    T findOneByCondition(SearchCondition<T> condition);

    /**
     * 根据字段和值查询一条数据
     *
     * @param field 字段
     * @param value 字段对应的值
     * @return 结果数据
     */
    T findOneByField(String field, Object value);


    /**
     * 根据字段和值查询列表数据
     *
     * @param field 字段
     * @param value 字段对应的值
     * @return 结果数据
     */
    List<T> findByField(String field, Object value);


    /**
     * 查询下拉列表数据
     *
     * @param condition 条件
     * @return 结果集
     */
    List<T> findByCombobx(SearchCondition<T> condition);


    /**
     * 根据条件查询总数
     *
     * @param condition 条件
     * @return 查询到的总数
     */
    long countByCondition(SearchCondition<T> condition);

    /**
     * 分页查询
     *
     * @param condition 条件查询对象
     * @return 根据条件查询的分页结果封装对象
     */
    PageResult<T> findByPage(SearchCondition<T> condition);


    /**
     * 根据条件查询记录是否存在
     *
     * @param condition
     * @return
     */
    boolean exists(SearchCondition<T> condition);

    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     * @return BaseResult对象 里面的obj 为添加后的主键值，即使主键是int类型的以已string类型放回
     */
    BaseResult<String> save(T entity);

    /**
     * 根据StringId 删除数据
     *
     * @param id string类型的id
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult<Integer> removeById(Object id);

    /**
     * 根据实体对象删除数据
     *
     * @param entity 实体对象
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult<Integer> remove(T entity);

    /**
     * 根据条件删除
     *
     * @param condition 条件
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult<Integer> removeByCondition(SearchCondition<T> condition);

    /**
     * 从数据库中删除一行数据
     *
     * @param entity 实体对象
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult<Integer> modifyEntity(T entity);


    BaseResult<Integer> modifyEntityByCondition(T newEntity, SearchCondition<T> condition);
    

}
