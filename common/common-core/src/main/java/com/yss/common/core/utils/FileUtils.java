package com.yss.common.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Author 杨森森
 * @Data 2024/5/6  13:56
 */
public class FileUtils {
    public static File transferToFile(InputStream fileInputStream, String filename) throws IOException {
        File tempFile = File.createTempFile(UUID.randomUUID().toString(), getSuffix(filename));
        FileOutputStream outputStream = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        return tempFile;
    }

    /**
     * 删除文件或文件夹，采用递归删除
     */
    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f);
                }
            }
        }
        // 删除文件夹本身
        file.delete();
    }

    /**
     * 获取文件后缀
     * @return 文件后缀，带”.“号
     */
    public static String getSuffix(String fileName) {
        int i = fileName.lastIndexOf('.');
        return fileName.substring(i);
    }

    /**
     * 判断文件夹是否为空
     */
    public static boolean isEmptyFolder(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            // 获取文件夹下的所有文件和子文件夹
            File[] files = folder.listFiles();
            // 判断文件夹是否为空
            return files == null || files.length <= 0;
        }
        return false;
    }
}
