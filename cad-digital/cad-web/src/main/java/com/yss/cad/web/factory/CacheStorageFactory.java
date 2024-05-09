package com.yss.cad.web.factory;

import com.yss.cad.web.enums.ParseState;
import com.yss.cad.web.storage.ICacheStorage;
import com.yss.cad.web.storage.RedisStorage;
import com.yss.common.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @Author 杨森森
 * @Data 2024/5/7  10:01
 */
@Component
public class CacheStorageFactory {

    @Value("${storage.cacheStorage}")
    private String cacheStoragePolicy;

    private ICacheStorage cacheStorage;

    @Autowired
    private RedisStorage redisStorage;


    @PostConstruct
    public void initialize() {
        switch (cacheStoragePolicy) {
            case "redis": {
                cacheStorage = redisStorage;
                break;
            }
            case "memory": {
                break;
            }
        }
    }

    public ICacheStorage getCacheStorage() {
        return cacheStorage;
    }

    /**
     * 获取当前状态
     */
    public ParseState state(String key) {
        return cacheStorage.state(key);
    }
}
