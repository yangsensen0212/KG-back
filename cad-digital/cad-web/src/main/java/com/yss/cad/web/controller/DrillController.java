package com.yss.cad.web.controller;

import com.yss.cad.web.sevice.DrillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/5/1  19:19
 */
@RestController
@RequestMapping("drill")
@Api("钻孔柱状图提取相关")
public class DrillController {
    @Autowired
    private DrillService drillService;

    @PostMapping(value = "parse", headers="content-type=multipart/form-data")
    @ApiOperation("解析为excel")
    public void parse(@RequestParam("zipFile") MultipartFile zipFile, @RequestHeader("Authorization") String token) throws IOException, InterruptedException {
        drillService.asyncParse(zipFile, token);
    }
}
