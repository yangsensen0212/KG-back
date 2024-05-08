package com.yss.common.core.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author 杨森森
 * @Data 2024/5/7  16:38
 */
public class BeanUtils {
    public static <T> T getBean(Class<T> type) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
        return context.getBean(type);
    }
}
