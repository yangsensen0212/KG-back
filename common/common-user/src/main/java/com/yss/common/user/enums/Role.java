package com.yss.common.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yss.common.core.enums.Valuable;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:54
 */
public enum Role implements Valuable {
    SUPER_ADMIN(1, "超级管理员"),
    ADMIN(2, "管理员"),
    PORTAL(3, "普通用户");

    /**
     * 数据库中的存储字段
     */
    @EnumValue
    private final Integer value;
    private final String name;

    Role(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * enum 对象调用value()方法返回与该enum对象对应的数据库中(jdbcType=unsigned tinyint)的值
     *
     * @return 与该enum对象对应的数据库中(jdbcType = unsigned tinyint)的值
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * 获取名称
     *
     * @return name
     */
    @Override
    public String getName() {
        return this.name;
    }
}
