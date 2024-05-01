package com.yss.cad.utils;

import java.io.File;
import java.nio.file.Files;

/**
 * @Author 杨森森
 * @Data 2024/4/14  14:06
 */
public class FileUtils {
    public static String getSuffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
    }

}
