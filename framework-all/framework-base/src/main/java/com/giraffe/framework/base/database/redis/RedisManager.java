package com.giraffe.framework.base.database.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.giraffe.framework.base.common.utils.EmptyUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;


public class RedisManager {


    private final static int SECONDS = 300;

    private JedisSentinelPool jedisSentinelPool;

    private JedisPool jedisPool;


    /**
     * 对一个key做过期设置
     *
     * @param key
     * @param seconds
     * @return
     * @author LiuYiJun
     * @date 2015年7月17日
     */
    public Long expireKey(String key, int seconds) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.expire(key, seconds);
            jedis.close();
        }
        return result;
    }


    /**
     * 初级键值对字符串存储
     *
     * @param key
     * @param value
     * @return 保存结果(成功为OK)
     * @
     */
    public String saveString(String key, String value) {
        String result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.set(key, value);
            jedis.close();
        }
        return result;
    }


    /**
     * 初级键值对字符串存储并且有时间限制
     *
     * @param key
     * @param value
     * @param seconds 存储的时间，以秒为单位
     * @return
     * @author LiuYiJun
     * @date 2015年7月16日
     */
    public String saveStringBySeconds(String key, String value, int seconds) {
        String result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.setex(key, seconds, value);
            jedis.close();
        }
        return result;
    }

    /**
     * 带有时间限制的存储，默认为5分钟,以秒为单位
     *
     * @param key
     * @param value
     * @return
     * @author LiuYiJun
     * @date 2015年7月22日
     */
    public String saveStringBySeconds(String key, String value) {
        String result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.setex(key, SECONDS, value);
            jedis.close();
        }
        return result;
    }

    /**
     * 根据key获取对应的value，普通的String类型的方式
     *
     * @param key
     * @return
     * @
     */
    public String getStringValueByKey(String key) {
        String result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.get(key);
            jedis.close();
        }
        return result;
    }

    /***
     * 根据key判断数据库中是否存在该键值对
     *
     * @param key
     * @return
     * @
     */
    public boolean exists(String key) {
        boolean result = false;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.exists(key);
            jedis.close();
        }
        return result;
    }

    /**
     * 根据key删除数据
     *
     * @param key
     * @return 删除的key的数量
     * @
     */
    public Long delete(String key) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.del(key);
            jedis.close();
        }
        return result;
    }

    /**
     * 保存Map
     *
     * @param key
     * @param map
     * @return 保存结果(成功为OK)
     * @
     */
    public String saveMap(String key, Map<String, String> map) {

        String result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hmset(key, map);
            jedis.close();
        }
        return result;
    }

    /**
     * 保存hash数据
     *
     * @param key
     * @param field
     * @param value
     * @return
     * @
     */
    public Long saveHash(String key, String field, String value) {

        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hset(key, field, value);
            jedis.close();
        }
        return result;
    }

    /**
     * 根据存储的key值查询map中指定的mapKey所对应的值
     *
     * @param storeKey 存储的key
     * @param mapKey   value中map的key
     * @return 查询到的值的集合
     * @
     */
    public List<String> getValuesFromMapByStoreKeyAndMapKey(String storeKey, String... mapKey) {
        List<String> result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hmget(storeKey, mapKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的Map的值的个数
     *
     * @param storeKey 存储的key
     * @return storeKey 所对应的Map的值的个数
     * @
     */
    public Long getLengthFromMapByStoreKey(String storeKey) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hlen(storeKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的Map的所有的key
     *
     * @param storeKey 存储的key
     * @return storeKey所对应的Map的所有的key
     * @
     */
    public Set<String> getMapKeyFromMapByStoreKey(String storeKey) {
        Set<String> result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hkeys(storeKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的Map的所有的value
     *
     * @param storeKey 存储的key
     * @return storeKey所对应的Map的所有的value
     * @
     */
    public List<String> getMapValueFromMapByStoreKey(String storeKey) {
        List<String> result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hvals(storeKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 保存List
     *
     * @param key
     * @param list
     * @return key对应下的value的个数。即list的size
     * @
     */
    public Long saveList(String key, List<String> list) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            for (String string : list) {
                result = jedis.lpush(key, string);
            }
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的List的值的个数
     *
     * @param storeKey 存储的key
     * @return storeKey所对应的List的值的个数
     * @
     */
    public Long getLengthFromListByStoreKey(String storeKey) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.llen(storeKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的List下的所有值
     *
     * @param storeKey 存储的key
     * @return storeKey所对应的List下的所有值
     * @
     */
    public List<String> getAllValuesFromListByStoreKey(String storeKey) {

        List<String> result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.lrange(storeKey, 0, -1);
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的List下的指定位置的值
     *
     * @param storeKey 存储的key
     * @param start    起始位置,从零开始
     * @param end      结束位置
     * @return storeKey所对应的List下的指定位置的值
     * @
     */
    public List<String> getValuesFromListByStoreKey(String storeKey, int start, int end) {
        List<String> result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.lrange(storeKey, start, end);
            jedis.close();
        }
        return result;
    }

    public Long deleteFromListByByStoreKeyAndValue(String storeKey, String value) {

        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.lrem(storeKey, 0, value);
            jedis.close();
        }
        return result;
    }

    /**
     * 保存Set
     *
     * @param key
     * @param set
     * @return key对应下的value的个数。即list的size
     * @
     */
    public Long saveSet(String key, Set<String> set) {

        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            for (String string : set) {
                result = jedis.sadd(key, string);
            }
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应Set下的值的个数
     *
     * @param storeKey
     * @return storeKey所对应Set下的值的个数
     * @
     */
    public Long getLengthFromSetByStoreKey(String storeKey) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.scard(storeKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 获取storeKey所对应的Set的所有值
     *
     * @param storeKey
     * @return storeKey所对应的Set的所有值
     * @
     */
    public Set<String> getAllValuesFromSetByStoreKey(String storeKey) {
        Set<String> result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.smembers(storeKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 判断一个值是否是storeKey所对应的Set的元素
     *
     * @param storeKey
     * @param value
     * @return
     * @
     */
    public boolean isSetValue(String storeKey, String value) {

        boolean result = false;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.sismember(storeKey, value);
            jedis.close();
        }
        return result;
    }

    /**
     * 从storeKey所对应的Set中删除指定的元素
     *
     * @param storeKey
     * @param value
     * @return
     * @
     */
    public Long deleteFromSetByStoreKeyAndValue(String storeKey, String value) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.srem(storeKey, value);
            jedis.close();
        }
        return result;
    }

    /**
     * 从storeKey所对应的Hash中删除指定的元素
     *
     * @param storeKey
     * @param value
     * @return
     * @
     */
    public Long deleteFromHashByStoreKeyAndMapKey(String storeKey, String...mapKey) {
        Long result = null;
        Jedis jedis = this.getJedisFromPool();
        if (EmptyUtil.isNotEmpty(jedis)) {
            result = jedis.hdel(storeKey, mapKey);
            jedis.close();
        }
        return result;
    }

    /**
     * 从连接池中获取jedis对象
     *
     * @param
     * @return
     * @author liuyijun
     * @date 2016/1/12
     */
    private Jedis getJedisFromPool() {
        Jedis jedis = null;
        if (EmptyUtil.isNotEmpty(jedisPool)) {
            jedis = jedisPool.getResource();
        } else {
            jedis = jedisSentinelPool.getResource();
        }
        return jedis;
    }

    public JedisSentinelPool getjedisSentinelPool() {
        return jedisSentinelPool;
    }

    public void setjedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
        this.jedisSentinelPool = jedisSentinelPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
