package com.yss.oauth;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @Author 杨森森
 * @Data 2024/4/10  16:34
 */
@SpringBootApplication(scanBasePackages = "com.yss")
@MapperScan("com.yss.common.user.dao")
@EnableAuthorizationServer
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
        System.out.println();
        System.out.println("------------program oauth started------------");
        System.out.println();
    }
}
