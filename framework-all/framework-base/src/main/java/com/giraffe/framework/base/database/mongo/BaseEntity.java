package com.giraffe.framework.base.database.mongo;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.annotations.Version;

import com.alibaba.fastjson.annotation.JSONField;
import com.giraffe.framework.base.common.utils.EmptyUtil;
import com.giraffe.framework.base.database.annotations.DefaultValueOnSave;
import com.giraffe.framework.base.database.annotations.DefaultValueType;

public abstract class BaseEntity implements Serializable {

    @JSONField(serialize = false)
    protected static final long serialVersionUID = -4457115108916026188L;
    @Id
    @JSONField(serialize = false)
    protected ObjectId id;

    @Transient
    protected String uid;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @DefaultValueOnSave(type = DefaultValueType.DATE)
    protected Date insertTime;
    @DefaultValueOnSave(type = DefaultValueType.DATE)
    protected Date lastUpdateTime;

    @Version
    @JSONField(serialize = false)
    protected Long version;

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


    public String getUid() {
        if (EmptyUtil.isEmpty(uid) && EmptyUtil.isNotEmpty(id)) {
            this.uid = id.toString();
        }
        return uid;
    }

    public void setUid(String uid) {
        if (EmptyUtil.isNotEmpty(uid)) {
            this.uid = uid;
            if (EmptyUtil.isEmpty(id)) {
                this.id = new ObjectId(uid);
            }
        }
    }
}
