package com.yss.dxf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 杨森森
 * @Data 2023/5/9  11:40
 * 多行文字
 */

@Getter
@AllArgsConstructor
public enum MTextEnum {
    /**
     * 文本名称
     */
    MTEXT_NAME("MTEXT","多文本名称"),
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
