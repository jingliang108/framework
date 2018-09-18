package com.tanjin.framework.base.database.base.service;

import java.io.Serializable;

import com.tanjin.framework.base.database.domain.returns.BaseResult;
import com.tanjin.framework.base.database.domain.search.SearchCondition;


public interface IBaseModifyService<T extends Serializable> {

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
