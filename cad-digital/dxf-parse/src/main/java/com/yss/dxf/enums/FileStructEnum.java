package com.yss.dxf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStructEnum {


    /**
     * 文件结束的标志
     */
    FILE_END("EOF", "文件结束"),

    /**
     * 实体段开始（DXF的四大模块)
     */
    ENTITIES_START("ENTITIES", "实体开始"),

    /**
     * 每个4大模块结束标志
     */
    END_SEC("ENDSEC", "模块结束"),
    ;

    /**
     * 字段码
     */
    private final String code;

    /**
     * 组码名称
     */
    private final String fieldName;

}
