package com.yss.dxf.exception;

import com.yss.dxf.enums.DxfAnalysisErrorEnum;

public class DxfAnalysisException extends DxfException {

    private static final long serialVersionUID = 2297430522517097373L;

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常
     */
    public DxfAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public DxfAnalysisException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param errorEnum DxfAnalysisErrorEnum
     */
    public DxfAnalysisException(DxfAnalysisErrorEnum errorEnum) {
        super(errorEnum.getMsg());
    }

}
