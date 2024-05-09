package com.yss.cad.web.controller;

import com.yss.cad.web.dto.ParseDto;
import com.yss.cad.web.sevice.DrillService;
import com.yss.cad.web.vo.ParseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
    public void parse(@RequestParam("zipFile") MultipartFile zipFile, @RequestHeader("Authorization") String token, ParseDto info) throws IOException, InterruptedException {
        drillService.asyncParse(zipFile, token, info);
    }

    @GetMapping(value = "get-parse-list")
    @ApiOperation("获取解析列表")
    public List<ParseVO> getParseList(@RequestHeader("Authorization") String token) throws IOException, ClassNotFoundException {
        return drillService.getParseList(token);
    }


}