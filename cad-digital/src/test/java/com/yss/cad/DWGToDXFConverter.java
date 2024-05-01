package com.yss.cad;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DWGToDXFConverter {

    public static void convertDWGtoDXF(String converterDirectory, String inputPath, String outputPath) {
        try {
            // 设置ODA File Converter的可执行文件名
            String converterExecutable = "TeighaFileConverter";
            // 构建命令行命令
            String[] commands = {
                    converterExecutable,
                    inputPath,
                    outputPath,
                    "ACAD2013",  // 目标DXF版本，如ACAD2013, ACAD2010等
                    "DXF",
                    "0",
                    "1",
                    "*.dwg"
            };

            // 创建ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cd", converterDirectory);
            processBuilder.command(commands);
            // 设置工作目录
//            processBuilder.directory(new File(converterDirectory));

            // 启动转换进程
            Process process = processBuilder.start();

            // 读取和打印输出信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待进程结束
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Conversion successful!");
            } else {
                System.out.println("Conversion failed with exit code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 调用转换函数
        String converterDirectory = "C:\\myself\\app\\Teigha";  // 转换器所在目录
        String inputPath = "C:\\Users\\pcforsen\\Downloads\\东滩-钻孔柱状图";
        String outputPath = "C:\\Users\\pcforsen\\Downloads\\东滩-钻孔柱状图-dxf";
        convertDWGtoDXF(converterDirectory, inputPath, outputPath);
    }
}
