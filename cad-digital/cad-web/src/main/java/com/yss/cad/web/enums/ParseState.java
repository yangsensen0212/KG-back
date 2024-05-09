package com.yss.cad.web.enums;

import com.yss.common.core.enums.Valuable;

/**
 * @Author 杨森森
 * @Data 2024/5/9  13:54
 */
public enum ParseState implements Valuable {
    FAIL("失败", 0),
    SUCCESS("成功", 1),
    PARSEING("解析中", 2);

    private String name;
    private int value;

    ParseState(String name, int value) {
        this.name = name;
        this.value = value;
    }
    /**
     * enum 对象调用value()方法返回与该enum对象对应的数据库中(jdbcType=unsigned tinyint)的值
     *
     * @return 与该enum对象对应的数据库中(jdbcType = unsigned tinyint)的值
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * 获取名称
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    public static ParseState getByName(String name) {
        for (ParseState state : values()) {
            if (state.name.equals(name)) {
                return state;
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + name);
    }
}
