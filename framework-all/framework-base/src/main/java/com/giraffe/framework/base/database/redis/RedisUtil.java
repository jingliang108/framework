package com.giraffe.framework.base.database.redis;

import java.util.Map;

import com.giraffe.framework.base.common.utils.EmptyUtil;

public class RedisUtil {

    public static Map<String, RedisManager> redisManagerMap;

    private static final String defaultRedisGroup = "commonRedisGroup";

    public static RedisManager getRedisManagerByName(String name) {
        return redisManagerMap.get(name);
    }

    public static String getStrValueByKey(String key) {
        return getStrValueByKey(key, defaultRedisGroup);
    }

    public static String getStrValueByKey(String key, String groupName) {
        RedisManager redisManager = redisManagerMap.get(groupName);
        if (EmptyUtil.isNotEmpty(redisManager)) {
            if (redisManager.exists(key)) {
                return redisManager.getStringValueByKey(key);
            }
        }
        return null;
    }

    public static String deleteByKey(String key, String groupName) {
        RedisManager redisManager = redisManagerMap.get(groupName);
        if (EmptyUtil.isNotEmpty(redisManager)) {
            redisManager.delete(key);
            return "success";
        } else {
            return "noRedisGroup";
        }
    }

    public static String deleteByKey(String key) {
        return deleteByKey(key, defaultRedisGroup);
    }

    public static String saveString(String key, String value, String groupName) {
        RedisManager redisManager = redisManagerMap.get(groupName);
        if (EmptyUtil.isNotEmpty(redisManager)) {
            redisManager.saveString(key, value);
            return "success";
        } else {
            return "noRedisGroup";
        }
    }

    public static String saveString(String key, String value) {
        return saveString(key, value, defaultRedisGroup);
    }

    public static String saveStringBySeconds(String key, String value, Integer seconds, String groupName) {
        RedisManager redisManager = redisManagerMap.get(groupName);
        if (EmptyUtil.isNotEmpty(redisManager)) {
            redisManager.saveStringBySeconds(key, value, seconds);
            return "success";
        } else {
            return "noRedisGroup";
        }
    }


    /**
     * 获取默认组的redisManager对象
     *
     * @return redisManager对象
     */
    public static RedisManager getRedisManager() {
        return getRedisManagerByGroupName(defaultRedisGroup);
    }

    /**
     * 根据组名称获取RedisManager
     *
     * @param groupName 组名
     * @return RedisManager 对象
     */
    public static RedisManager getRedisManagerByGroupName(String groupName) {
        return redisManagerMap.get(groupName);
    }

    public static String saveStringBySeconds(String key, String value, Integer seconds) {
        return saveStringBySeconds(key, value, seconds, defaultRedisGroup);
    }

    public Map<String, RedisManager> getRedisManagerMap() {
        return redisManagerMap;
    }

    public void setRedisManagerMap(Map<String, RedisManager> redisManagerMap) {
        RedisUtil.redisManagerMap = redisManagerMap;
    }

}
