package com.yss.common.redis.service.impl;

import com.yss.common.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @Author 杨森森
 * @Data 2024/4/10  14:51
 */
@Service
public class RedisServiceImpl<T> implements IRedisService<T> {
    @Autowired
    private RedisTemplate<String, T> redisTemplate;


    /**
     * 设置缓存
     *
     * @param key   键值
     * @param value 存储值
     */
    @Override
    public void setCacheObject(String key, T value) {
        ValueOperations<String, T> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * 得到缓存对象
     *
     * @param key 键值
     * @return 缓存的对象
     */
    @Override
    public T getCacheObject(String key) {
        ValueOperations<String, T> ops = redisTemplate.opsForValue();
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
    public void setCacheList(String key, List<T> dataList) {
    }

    /**
     * 追加到list数组中，无元素时自动初始化
     *
     * @param key  缓存的键值
     * @param data 带添加的值
     */
    @Override
    public void addToCacheList(String key, T data) {
        ListOperations<String, T> ops = redisTemplate.opsForList();
        ops.rightPush(key, data);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @Override
    public List<T> getCacheList(String key) {
        ListOperations<String, T> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     */
    @Override
    public void setCacheSet(String key, Set<T> dataSet) {
    }

    /**
     * 获得缓存的set
     *
     * @param key 键值
     * @return set集合
     */
    @Override
    public <T1> Set<T1> getCacheSet(String key) {
        return null;
    }

    /**
     * 缓存Map
     *
     * @param key     键值
     * @param dataMap map对象
     */
    @Override
    public void setCacheMap(String key, Map<String, T> dataMap) {
    }

    /**
     * 缓存Map
     *
     * @param key    键值
     * @param mapKey map的key值
     * @param data   map对象值
     */
    @Override
    public void addToCacheMap(String key, String mapKey, T data) {
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
    public Map<String, T> getCacheMap(String key) {
        return null;
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
}
