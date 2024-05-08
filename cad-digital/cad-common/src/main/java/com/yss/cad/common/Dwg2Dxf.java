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
    public int transfer(String inputDir, String outputDir) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(String.format("cmd.exe /c cd %s && TeighaFileConverter %s %s ACAD2013 DXF 0 1 *.dwg",
                converterDirectory, inputDir, outputDir));
//        // 获取命令执行结果
//        InputStream inputStream = process.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//        reader.close();
//
//        // 等待命令执行完成
        int exitCode = process.waitFor();
        return exitCode;
    }
}