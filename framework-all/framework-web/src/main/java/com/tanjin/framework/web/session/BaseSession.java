package com.tanjin.framework.web.session;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;
import com.tanjin.framework.base.common.utils.EmptyUtil;

public class BaseSession implements Serializable {

    protected static ThreadLocal<BaseSession> threadLocalUserSession = new ThreadLocal<BaseSession>();


    public static void setUserSession(BaseSession userSession) {
        threadLocalUserSession.set(userSession);
    }

    /**
     * 获取登录用户的ID
     *
     * @return
     * @author LiuYiJun
     * @date 2015年7月16日
     */
    public static Integer getUserIntId() {
        BaseSession userSession = (BaseSession) threadLocalUserSession.get();
        if (userSession == null) {
            return null;
        } else {
            return userSession.getIntId();
        }
    }


    public static String getUserStrId() {
        BaseSession userSession = (BaseSession) threadLocalUserSession.get();
        if (userSession == null) {
            return null;
        } else {
            return userSession.getUid();
        }
    }

    //用户Integer ID
    protected Integer intId;

    //用户的String类型的id
    protected String uid;

    /**
     * 扩展字段，存放些特殊的值
     */
    protected Map<String, Object> properties;


    public Integer getIntId() {
        return intId;
    }

    public void setIntId(Integer intId) {
        this.intId = intId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public Map<String, Object> getProperties() {
        return properties;
    }

    public void clearProperty(String name) {
        if (EmptyUtil.isNotEmpty(this.properties)) {
            this.properties.remove(name);
        }

    }

    public void putProperty(String name, String value) {
        if (EmptyUtil.isEmpty(this.properties)) {
            this.properties = Maps.newHashMap();
        }
        this.properties.put(name, value);
    }
}
