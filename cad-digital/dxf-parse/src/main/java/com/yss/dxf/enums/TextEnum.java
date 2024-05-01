package com.yss.dxf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * dxf 文本枚举
 *
 */
@Getter
@AllArgsConstructor
public enum TextEnum {
    /**
     * 文本名称
     */
    TEXT_NAME("TEXT","文本名称"),
    /**
     * 文本内容
     */
    TEXT_CONTENT("1","文本内容"),
    /**
     * 图层名称
     */
    LAYER_NAME("8", "图层名称"),
    /**
     * 中心点x坐标
     */
    COORDINATE_X("10", "中心点x坐标"),
    /**
     * 中心点y坐标
     */
    COORDINATE_Y("20", "中心点y坐标"),
    /**
     * 中心点z坐标
     */
    COORDINATE_Z("30", "中心点z坐标");
    /**
     * 组码
     */
    private final String code;

    /**
     * 组码名称
     */
    private final String fieldName;
}
