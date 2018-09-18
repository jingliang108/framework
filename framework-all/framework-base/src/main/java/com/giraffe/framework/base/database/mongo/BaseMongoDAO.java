package com.giraffe.framework.base.database.mongo;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.giraffe.framework.base.common.utils.BasicFieldUtil;
import com.giraffe.framework.base.common.utils.EmptyUtil;
import com.giraffe.framework.base.common.utils.ModelSetFieldDefaultValueUtil;
import com.giraffe.framework.base.common.utils.ReflectUtil;
import com.giraffe.framework.base.database.domain.page.PageResult;
import com.giraffe.framework.base.database.domain.page.PageSearch;
import com.giraffe.framework.base.database.domain.search.RangeCondition;
import com.giraffe.framework.base.database.domain.search.RangeConditionType;
import com.giraffe.framework.base.database.domain.search.SearchCondition;

public class BaseMongoDAO<T extends Serializable, K extends Serializable> implements DAO<T, K> {

    @Autowired
    private Datastore ds;

    protected Class<T> entityClazz;

    public BaseMongoDAO(Class<T> entityClazz) {
        this.entityClazz = entityClazz;
    }


    public Query<T> createQuery() {
        return ds.createQuery(entityClazz);
    }

    public Class<T> getEntityClass() {
        return entityClazz;
    }

    // ***************新增操作********************//
    public Key<T> save(T entity) {
        ModelSetFieldDefaultValueUtil.setFieldDefaultValue(entity);
        return ds.save(entity);
    }

    public Key<T> save(T entity, WriteConcern wc) {
        ModelSetFieldDefaultValueUtil.setFieldDefaultValue(entity);
        return ds.save(entity, wc);
    }

    // ***************删除操作********************//
    public WriteResult delete(T entity) {
        return ds.delete(entity);
    }

    public WriteResult delete(T entity, WriteConcern wc) {
        return ds.delete(entity, wc);
    }

    public WriteResult deleteById(K id) {
        return ds.delete(entityClazz, id);
    }


    public WriteResult deleteByQuery(Query<T> q) {
        return ds.delete(q);
    }

    public WriteResult deleteByConditon(SearchCondition<T> condition) {
        Query<T> q = this.createBaseQueryBySearchCondition(condition);
        return ds.delete(q);
    }

    // ***************简易查询操作********************//
    public T get(K id) {
        return ds.get(entityClazz, id);
    }

    /**
     * Converts from a List<Key> to their id values
     */
    protected List<?> keysToIds(final List<Key<T>> keys) {
        final List<Object> ids = new ArrayList<Object>(keys.size() * 2);
        for (final Key<T> key : keys) {
            ids.add(key.getId());
        }
        return ids;
    }

    @SuppressWarnings("unchecked")
    public List<K> findIds() {
        return (List<K>) keysToIds(ds.find(entityClazz).asKeyList());
    }

    @SuppressWarnings("unchecked")
    public List<K> findIds(String key, Object value) {
        return (List<K>) keysToIds(ds.find(entityClazz, key, value).asKeyList());
    }

    @SuppressWarnings("unchecked")
    public List<K> findIds(Query<T> q) {
        return (List<K>) keysToIds(q.asKeyList());
    }

    public Key<T> findOneId() {
        return findOneId(ds.find(entityClazz));
    }

    public Key<T> findOneId(String key, Object value) {
        return findOneId(ds.find(entityClazz, key, value));
    }

    public Key<T> findOneId(Query<T> q) {
        Iterator<Key<T>> keys = q.fetchKeys().iterator();
        return keys.hasNext() ? keys.next() : null;
    }

    public boolean exists(String key, Object value) {
        return exists(ds.find(entityClazz, key, value));
    }

    public boolean exists(Query<T> q) {
        return ds.getCount(q) > 0;
    }

    public long count() {
        return ds.getCount(entityClazz);
    }

    public long count(String key, Object value) {
        return count(ds.find(entityClazz, key, value));
    }

    public long count(Query<T> q) {
        return ds.getCount(q);
    }

    public T findOne(String key, Object value) {
        return ds.find(entityClazz, key, value).get();
    }

    public T findOne(Query<T> q) {
        return q.get();
    }

    public QueryResults<T> find() {
        return createQuery();
    }

    public QueryResults<T> find(Query<T> q) {
        return q;
    }

    public void ensureIndexes() {
        ds.ensureIndexes(entityClazz);
    }

    public DBCollection getCollection() {
        return ds.getCollection(entityClazz);
    }

    public Datastore getDatastore() {
        return ds;
    }

    /**
     * 根据uid查询，该实体的id必须是org.bson.types.ObjectId
     *
     * @param uid              ObjectId中的字符串
     * @param onlyPrimaryField 是否只返回该实体类的主要属性，过滤掉其他的属性
     * @return
     */
    public T findById(String uid, boolean onlyPrimaryField, String... defaultPrimaryFields) {
        Query<T> query = this.createQuery().field("_id").equal(new ObjectId(uid));
        if (onlyPrimaryField) {
            query.retrievedFields(true, BasicFieldUtil.getPrimaryFiled(entityClazz, defaultPrimaryFields));
        }
        return findOne(query);
    }


    public T findById(String uid, String... selectColumns) {
        Query<T> query = this.createQuery().field("_id").equal(new ObjectId(uid));
        if (EmptyUtil.isNotEmpty(selectColumns) && selectColumns.length > 0) {
            query.retrievedFields(true, selectColumns);
        }
        return findOne(query);
    }

    /**
     * 根据条件查询，无分页，注意条件的范围，尽量使用分页查询
     */
    public List<T> findByCondition(SearchCondition<T> condition) {
        Query<T> q = createQueryBySearchCondition(condition, false);
        //Top处理
        this.doTopCondition(q, condition.getTop());
        return q.asList();
    }


    public T findOneByCondition(SearchCondition<T> condition) {
        Query<T> q = createQueryBySearchCondition(condition, false);
        return this.findOne(q);
    }


    // ***************下拉列表数据查询，针对页面中的下拉列表********************//

    public List<T> findByCombobx(SearchCondition<T> condition) {
        Query<T> query = createQueryBySearchCondition(condition, true);
        //Top处理
        this.doTopCondition(query, condition.getTop());
        return query.asList();
    }

    // ***************分页操作********************//

    /**
     * 通用分页
     *
     * @param condition
     * @return
     */
    public PageResult<T> findByPage(SearchCondition<T> condition) {
        PageResult<T> pageResult = new PageResult<T>();
        Query<T> query = createQueryBySearchCondition(condition, false);
        if (EmptyUtil.isEmpty(condition.getPageSearch())) {
            condition.setPageSearch(new PageSearch());
        }
        query.offset(condition.getPageSearch().getCurrentResult()).limit(condition.getPageSearch().getRows());
        pageResult.setRows(query.asList());
        pageResult.setTotal(ds.getCount(query));
        return pageResult;
    }

    public boolean exists(SearchCondition<T> condition) {
        Query<T> q = this.createBaseQueryBySearchCondition(condition);
        return ds.getCount(q) > 0;
    }


    public long countByCondition(SearchCondition<T> condition) {
        Query<T> q = this.createBaseQueryBySearchCondition(condition);
        return ds.getCount(q);
    }


    private Query<T> createQueryBySearchCondition(SearchCondition<T> condition, boolean isCombobox) {
        Query<T> query = this.createBaseQueryBySearchCondition(condition);
        //排序处理
        this.doOrderByCondition(query, condition.getOrderByConditions());
        //筛选字段
        this.doSelectedField(condition, query, isCombobox);
        return query;
    }

    private Query<T> createBaseQueryBySearchCondition(SearchCondition<T> condition) {
        Query<T> query = this.createQueryByExample(condition.getModelExample());
        //区间查询处理
        this.doRangeCondition(query, condition.getRangeConditions());
        //模糊查询处理
        this.doLikeCondition(query, condition.getLikeConditions());
        //模糊查询处理（以xx开头）
        doStartWithCondition(query, condition.getStartWithConditions());
        //模糊查询处理（以xx结尾）
        doEndWithCondition(query, condition.getEndWithConditions());
        //In查询处理
        doInCondition(query, condition.getInConditions());
        //NotIn查询
        doNotInCondition(query, condition.getNotInConditions());

        return query;
    }


    /**
     * 查询字段筛选处理
     *
     * @param condition
     * @param query
     * @param isCombobox
     */
    private void doSelectedField(SearchCondition<T> condition, Query<T> query, boolean isCombobox) {
        if (EmptyUtil.isNotEmpty(condition.getSelectColumns()) && condition.getSelectColumns().length > 0) {
            query.retrievedFields(true, condition.getSelectColumns());
        } else {
            String[] fields = null;
            if (isCombobox) {
                fields = BasicFieldUtil.getComboboxFiled(this.entityClazz);
            } else {
                if (condition.isOnlyBasicField()) {
                    fields = BasicFieldUtil.getPrimaryFiled(entityClazz);
                }
            }
            if (EmptyUtil.isNotEmpty(fields) && fields.length > 0) {
                query.retrievedFields(true, fields);
            }
        }
    }

    private Query<T> createQueryByExample(T example) {
        Query<T> q = createQuery();
        if (EmptyUtil.isNotEmpty(example)) {
            this.createQueryOrUpdateOperationsByExample(example, null, q);
        }
        return q;
    }


    /**
     * 对查询条件的时间进行处理
     *
     * @param q
     * @param rangeConditions
     */
    private void doRangeCondition(Query<T> q, List<RangeCondition> rangeConditions) {
        if (EmptyUtil.isNotEmpty(rangeConditions) && rangeConditions.size() > 0) {
            for (RangeCondition dateCondition : rangeConditions) {
                if (EmptyUtil.isNotEmpty(dateCondition.getStartValue()) || EmptyUtil.isNotEmpty(dateCondition.getEndValue())) {
                    if (EmptyUtil.isNotEmpty(dateCondition.getType())) {
                        RangeConditionType dateConditionType = dateCondition.getType();
                        switch (dateConditionType) {
                            case GreaterThan:
                                q.criteria(dateCondition.getField()).greaterThan(dateCondition.getStartValue());
                                break;
                            case GreaterThanOrEqual:
                                q.criteria(dateCondition.getField()).greaterThanOrEq(dateCondition.getStartValue());
                                break;
                            case LessThan:
                                q.criteria(dateCondition.getField()).lessThan(dateCondition.getStartValue());
                                break;
                            case LessThanOrEqual:
                                q.criteria(dateCondition.getField()).lessThanOrEq(dateCondition.getStartValue());
                                break;
                            case Equal:
                                q.criteria(dateCondition.getField()).equal(dateCondition.getStartValue());
                                break;
                            case Between:
                                if (EmptyUtil.isNotEmpty(dateCondition.getEndValue()) && EmptyUtil.isNotEmpty(dateCondition.getStartValue())) {
                                    q.criteria(dateCondition.getField()).greaterThanOrEq(dateCondition.getStartValue()).criteria(dateCondition.getField()).lessThanOrEq(dateCondition.getEndValue());
                                } else if (EmptyUtil.isNotEmpty(dateCondition.getEndValue()) && EmptyUtil.isEmpty(dateCondition.getStartValue())) {
                                    q.criteria(dateCondition.getField()).lessThanOrEq(dateCondition.getEndValue());
                                } else if (EmptyUtil.isEmpty(dateCondition.getEndValue()) && EmptyUtil.isNotEmpty(dateCondition.getStartValue())) {
                                    q.criteria(dateCondition.getField()).greaterThanOrEq(dateCondition.getEndValue());
                                }
                                break;
                        }
                    }
                }
            }
        }
    }


    /**
     * 对条数进行处理
     *
     * @param q
     * @param top
     */
    private void doTopCondition(Query<T> q, Integer top) {
        if (EmptyUtil.isNotEmpty(top)) {
            q.offset(0).limit(top);
        }
    }

    /**
     * 对查询条件的时间进行处理
     *
     * @param q
     * @param orderByCondition
     */
    private void doOrderByCondition(Query<T> q, Map<String, String> orderByCondition) {
        String orderStr = "";
        if (EmptyUtil.isNotEmpty(orderByCondition) && orderByCondition.size() > 0) {
            for (String key : orderByCondition.keySet()) {
                if ("desc".equals(orderByCondition.get(key))) {
                    orderStr += "-" + key + ",";
                } else {
                    orderStr += key + ",";
                }
            }
            orderStr = orderStr.substring(0, orderStr.length());
        } else {
            orderStr = "-insertTime";
        }
        q.order(orderStr);
    }

    /**
     * 模糊查询处理
     *
     * @param q
     * @param likeCondition
     */
    private void doLikeCondition(Query<T> q, Map<String, String> likeCondition) {
        if (EmptyUtil.isNotEmpty(likeCondition) && likeCondition.size() > 0) {
            for (String key : likeCondition.keySet()) {
                q.field(key).contains(likeCondition.get(key));
            }
        }
    }

    private void doStartWithCondition(Query<T> q, Map<String, String> startWithCondition) {
        if (EmptyUtil.isNotEmpty(startWithCondition)) {
            for (String key : startWithCondition.keySet()) {
                q.field(key).startsWith(startWithCondition.get(key));
            }
        }
    }

    private void doEndWithCondition(Query<T> q, Map<String, String> endWithCondition) {
        if (EmptyUtil.isNotEmpty(endWithCondition)) {
            for (String key : endWithCondition.keySet()) {
                q.field(key).endsWith(endWithCondition.get(key));
            }
        }
    }

    private void doInCondition(Query<T> q, Map<String, List<Object>> inCondition) {
        if (EmptyUtil.isNotEmpty(inCondition)) {
            for (String key : inCondition.keySet()) {
                q.field(key).in(inCondition.get(key));
            }
        }
    }


    private void doNotInCondition(Query<T> q, Map<String, List<Object>> notInCondition) {
        if (EmptyUtil.isNotEmpty(notInCondition)) {
            for (String key : notInCondition.keySet()) {
                q.field(key).notIn(notInCondition.get(key));
            }
        }
    }

    // ***************更新操作********************//

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */

    public int updateEntity(T entity) {
        try {
            ObjectId id = (ObjectId) ReflectUtil.getValueByAnnotation(entity, Id.class);
            Query<T> q = createQuery().field("_id").equal(id);
            UpdateOperations<T> ops = this.createBaseFilesUpdate(entity);
            UpdateResults results = update(q, ops);
            return results.getUpdatedCount();
        } catch (Exception e) {
            return 0;
        }
    }


    public int updateEntityByCondition(T newEntity, SearchCondition<T> condition) {
        Query<T> q = this.createBaseQueryBySearchCondition(condition);
        UpdateOperations<T> ops = this.createBaseFilesUpdate(newEntity);
        UpdateResults results = update(q, ops);
        return results.getUpdatedCount();
    }


    public UpdateOperations<T> createUpdateOperations() {
        return ds.createUpdateOperations(entityClazz);
    }

    public UpdateResults updateFirst(Query<T> q, UpdateOperations<T> ops) {
        ops.set("lastUpdateTime", new Date());
        return ds.updateFirst(q, ops);
    }

    public UpdateResults update(Query<T> q, UpdateOperations<T> ops) {
        ops.set("lastUpdateTime", new Date());
        return ds.update(q, ops);
    }


    /**
     * 创建实体更新字段
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private UpdateOperations<T> createBaseFilesUpdate(T entity) {
        UpdateOperations<T> ops = createUpdateOperations();
        this.createQueryOrUpdateOperationsByExample(entity, ops, null);
        return ops;
    }


    private void createQueryOrUpdateOperationsByExample(T entity, UpdateOperations<T> ops, Query<T> q) {
        Field[] fields = ReflectUtil.getAllFieldsByCutOff(entity, BaseEntity.class);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!"serialVersionUID".equals(fieldName)) {
                Transient tr = field.getAnnotation(Transient.class);
                if (EmptyUtil.isEmpty(tr)) {
                    Object value = null;
                    try {
                        value = ReflectUtil.getValueByFieldName(entity, fieldName);
                        if (EmptyUtil.isNotEmpty(value)) {
                            if (EmptyUtil.isNotEmpty(ops)) {
                                ops.set(fieldName, value);
                            } else {
                                if (field.getType() != Date.class) {
                                    q.field(fieldName).equal(value);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
