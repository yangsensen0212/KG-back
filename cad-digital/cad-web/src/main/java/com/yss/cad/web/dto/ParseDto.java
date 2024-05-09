package com.yss.cad.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 杨森森
 * @Data 2024/5/9  14:30
 */
@Data
public class ParseDto implements Serializable {
    private String name;

    private Date date = new Date();
}
