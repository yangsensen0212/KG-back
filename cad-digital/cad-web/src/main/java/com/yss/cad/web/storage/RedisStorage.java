package com.yss.cad.web.storage;

import com.yss.common.core.utils.BeanUtils;
import com.yss.common.redis.service.IRedisService;
import com.yss.common.redis.service.impl.RedisServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:22
 */
@Component
public class RedisStorage implements ICacheStorage{

    @Autowired
    private IRedisService<String> redisService;

//    public RedisStorage() {
//        this.redisService = BeanUtils.getBean(RedisServiceImpl.class);
//    }

    /**
     * 将信息存储至缓存
     *
     * @param token    令牌
     * @param key      当前key
     * @param fileName 文件名称
     * @param filePath 文件路径
     */
    @Override
    public void storage(String token, String key, String fileName, String filePath) {
        redisService.addToCacheList(token, key);
        redisService.addToCacheMap(key, fileName, filePath);
    }
}
