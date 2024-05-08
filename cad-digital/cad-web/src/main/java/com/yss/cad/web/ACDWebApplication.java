package com.yss.cad.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author 杨森森
 * @Data 2024/4/30  22:25
 */
@SpringBootApplication(scanBasePackages = "com.yss")
@EnableAsync
public class ACDWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ACDWebApplication.class, args);
        System.out.println();
        System.out.println("------------program cad-web started------------");
        System.out.println();
    }
}
