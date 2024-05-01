package com.yss.cad.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 杨森森
 * @Data 2024/5/1  19:19
 */
@RestController("drill")
public class DrillController {
    @RequestMapping("parse")
    public void parse(MultipartFile[] files) {

    }
}
