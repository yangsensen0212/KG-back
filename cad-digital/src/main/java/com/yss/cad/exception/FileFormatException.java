package com.yss.cad.exception;

/**
 * @Author 杨森森
 * @Data 2024/4/14  13:58
 */
public class FileFormatException extends RuntimeException {
    public FileFormatException() {
        super("文件格式异常");
    }
}
