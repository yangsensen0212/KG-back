package com.yss.cad.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/4/30  22:17
 */
@Component
public class Dwg2Dxf {
    @Value("${cad.converterDirectory}")
    private String converterDirectory;
    public void transfer(String inputDir, String outputDir) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec(String.format("cmd.exe /c cd %s && TeighaFileConverter %s %s ACAD2013 DXF 0 1 *.dwg",
                converterDirectory, inputDir, outputDir));
    }
}