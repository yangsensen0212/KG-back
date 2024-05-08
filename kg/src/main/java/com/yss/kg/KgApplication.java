package com.yss.kg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author 杨森森
 * @Data 2024/4/9  21:56
 */
@SpringBootApplication(scanBasePackages = "com.yss")
public class KgApplication {
    public static void main(String[] args) {
        SpringApplication.run(KgApplication.class, args);
        System.out.println();
        System.out.println("---------------------Program kg started---------------------");
        System.out.println();
    }
}
