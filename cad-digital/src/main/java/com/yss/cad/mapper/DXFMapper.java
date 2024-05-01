package com.yss.cad.mapper;

import com.yss.dxf.DXF;
import com.yss.dxf.collector.DxfCollector;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/4/13  22:10
 */
@Component
public class DXFMapper {
    public DxfCollector getCollector(String fileName) throws IOException {
        File file = new File(fileName);
        return getCollector(file);
    }
    public DxfCollector getCollector(File file) throws IOException {
        return DXF.build(new FileInputStream(file), "UTF-8");
    }
}
