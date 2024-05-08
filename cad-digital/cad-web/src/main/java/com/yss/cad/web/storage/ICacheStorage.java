package com.yss.cad.web.storage;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:04
 */
public interface ICacheStorage {
    /**
     * 将信息存储至缓存
     * @param token 令牌
     * @param key 当前key
     * @param fileName 文件名称
     * @param filePath 文件路径
     */
    void storage(String token, String key, String fileName, String filePath);
}
