package com.yss.dxf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArcEnum {

    /**
     * 弧线名
     */
    ARC_NAME("ARC", "弧线名"),

    /**
     * 图层名称
     */
    LAYER_NAME("8", "图层名称"),

    /**
     * x坐标
     */
    COORDINATE_X("10", "x坐标"),

    /**
     * y坐标
     */
    COORDINATE_Y("20", "y坐标"),

    /**
     * z坐标
     */
    COORDINATE_Z("30", "z坐标"),

    /**
     * 圆弧半径
     */
    ARC_RADIUS("40","圆弧半径"),

    /**
     * 圆弧起始角度
     */
    ARC_START_ANGLE("50","圆弧起始角度"),

    /**
     * 圆弧终止角度
     */
    ARC_END_ANGLE("51","圆弧终止角度");

    /**
     * 组码
     */
    private String code;

    /**
     * 组码名称
     */
    private String fieldName;

}
