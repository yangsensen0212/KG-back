package com.yss.common.core.enums;

import java.io.Serializable;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:56
 * 用于给管理SQL中status的unsigned tinyint的Enum类实现
 */
public interface Valuable extends Serializable {
    /**
     * enum 对象调用value()方法返回与该enum对象对应的数据库中(jdbcType=unsigned tinyint)的值
     * @return 与该enum对象对应的数据库中(jdbcType=unsigned tinyint)的值
     */
    int getValue();
    /**
     * 获取名称
     * @return name
     */
    String getName();
}
