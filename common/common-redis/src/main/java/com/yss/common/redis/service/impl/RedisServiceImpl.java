package com.yss.common.redis.service.impl;

import com.yss.common.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author 杨森森
 * @Data 2024/4/10  14:51
 */
@Service
public class RedisServiceImpl implements IRedisService<String> {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 设置缓存
     *
     * @param key   键值
     * @param value 存储值
     */
    @Override
    public void setCacheObject(String key, String  value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * 得到缓存对象
     *
     * @param key 键值
     * @return 缓存的对象
     */
    @Override
    public String getCacheObject(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键值
     */
    @Override
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     */
    @Override
    public void setCacheList(String key, List<String> dataList) {
    }

    /**
     * 追加到list数组中，无元素时自动初始化
     *
     * @param key  缓存的键值
     * @param data 带添加的值
     */
    @Override
    public void addToCacheList(String key, String data) {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.rightPush(key, data);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @Override
    public List<String> getCacheList(String key) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     */
    @Override
    public void setCacheSet(String key, Set<String> dataSet) {
    }

    /**
     * 往set添加
     *
     */
    @Override
    public void addCatchSet(String key, String value) {
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.add(key, value);
    }

    /**
     * 获得缓存的set
     *
     * @param key 键值
     * @return set集合
     */
    @Override
    public  Set<String> getCacheSet(String key) {
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        return ops.members(key);
    }

    /**
     * 缓存Map
     *
     * @param key     键值
     * @param dataMap map对象
     */
    @Override
    public void setCacheMap(String key, Map<String, String> dataMap) {
    }

    /**
     * 缓存Map
     *
     * @param key    键值
     * @param mapKey map的key值
     * @param data   map对象值
     */
    @Override
    public void addToCacheMap(String key, String mapKey, String data) {
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
        ops.put(mapKey, data);
    }

    /**
     * 获得缓存的Map
     *
     * @param key 键值
     * @return map对象
     */
    @Override
    public Map<String, String> getCacheMap(String key) {
        Map<Object, Object> rawMap = redisTemplate.opsForHash().entries(key);
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : rawMap.entrySet()) {
            map.put(entry.getKey().toString(), (String) entry.getValue());
        }
        return map;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    @Override
    public Collection<String> keys(String pattern) {
        return null;
    }

    /**
     * 设置过期时间
     *
     * @param key key
     */
    @Override
    public void setExpired(String key, long second) {
        redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 从list中删除所有等于value的值
     *
     * @param key   key值
     * @param value list中的某value
     */
    @Override
    public void removeFromList(String key, String value) {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.remove(key, 0, value);
    }

    /**
     * 从set中删除所有等于value的值
     *
     * @param key   key值
     * @param value set中的某value
     */
    @Override
    public void removeFromSet(String key, String value) {
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.remove(key, value);
    }
}
