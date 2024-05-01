package com.yss.cad.exception;

import lombok.Data;

/**
 * @Author 杨森森
 * @Data 2024/4/14  11:29
 */
public class NotDirException extends RuntimeException{
    public NotDirException(){
        super("文件夹异常");
    }
}
