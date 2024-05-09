package com.yss.cad.web.listener;

import com.yss.cad.web.storage.RedisStorage;
import com.yss.common.redis.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * @Author 杨森森
 * @Data 2024/5/8  15:40
 * redis键值过期监听
 */
@Component
@Slf4j
public class RedisExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisStorage redisStorage;

    public RedisExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message) {
        String key = new String(message.getBody());
        if (redisStorage.exist(key)) {
            redisStorage.delete(key);
        }
    }
}
