package com.yss.common.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author 杨森森
 * @Data 2024/4/10  14:22
 */
public interface IRedisService<T> {
    /**
     * 设置缓存
     * @param key 键值
     * @param value 存储值
     */
    void setCacheObject(String key, T value);

    /**
     * 得到缓存对象
     * @param key 键值
     * @return 缓存的对象
     */
    T getCacheObject(String key);

    /**
     * 删除缓存
     * @param key 键值
     */
    void deleteCache(String key);


    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     */
    void setCacheList(String key, List<T> dataList);


    /**
     * 追加到list数组中，无元素时自动初始化
     *
     * @param key 缓存的键值
     * @param data 带添加的值
     */
    void addToCacheList(String key, T data);

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    List<T> getCacheList(String key);

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     */
    void setCacheSet(String key, Set<T> dataSet);

    /**
     * 往set添加
     */
    void addCatchSet(String key, T value);

    /**
     * 获得缓存的set
     *
     * @param key 键值
     * @return set集合
     */
    <T> Set<T> getCacheSet(String key);

    /**
     * 缓存Map
     *
     * @param key 键值
     * @param dataMap map对象
     */
    void setCacheMap(String key, Map<String, T> dataMap);

    /**
     * 缓存Map
     *
     * @param key 键值
     * @param data map对象值
     */
    void addToCacheMap(String key, String mapKey, T data);

    /**
     * 获得缓存的Map
     *
     * @param key 键值
     * @return map对象
     */
    <T> Map<String, T> getCacheMap(String key);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> keys(String pattern);

    /**
     * 设置过期时间
     * @param key key
     * @param second 秒
     */
    void setExpired(String key, long second);

    /**
     * 从list中删除所有等于value的值
     * @param key key值
     * @param value list中的某value
     */
    void removeFromList(String key, String value);
    /**
     * 从set中删除所有等于value的值
     * @param key key值
     * @param value set中的某value
     */
    void removeFromSet(String key, String value);
}
