package com.yss.kg.controller;

import com.yss.common.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 杨森森
 * @Data 2024/4/9  22:17
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private IRedisService<String> redisService;

    @GetMapping("hello")
    public String hallo() {
        return "hello";
    }


    @GetMapping("set")
    public void setValue(String key, String value){
        redisService.setCacheObject(key, value);
    }
    @GetMapping("get")
    public String getValue(String key){
        String s = redisService.getCacheObject(key);
        return s;
    }
}
