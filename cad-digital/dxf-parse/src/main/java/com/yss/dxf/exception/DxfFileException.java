package com.yss.dxf.exception;

import com.yss.dxf.enums.DxfFileErrorEnum;

public class DxfFileException extends DxfException {

    private static final long serialVersionUID = 2297430522517097373L;

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常
     */
    public DxfFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
             * @param message 错误信息
     */
    public DxfFileException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param errorEnum DxfFileErrorEnum
     */
    public DxfFileException(DxfFileErrorEnum errorEnum) {
        super(errorEnum.getMsg());
    }

}
