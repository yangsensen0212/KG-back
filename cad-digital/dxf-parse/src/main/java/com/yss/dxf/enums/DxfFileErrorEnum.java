package com.yss.dxf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DxfFileErrorEnum {

    /**
     * DXF文件后缀错误
     */
    DXF_FILE_ERROR("0001", "DXF文件类型错误");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

}
