package com.yss.common.core.utils;


import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author 杨森森
 * @Data 2024/5/6  11:12
 */
public class ZipUtils {
    /**
     * 解压zip文件夹
     */
    public static void unZipFiles(File zipFile, File unZipDir) throws IOException {
        ZipFile zipInput = new ZipFile(zipFile, "GBK");
        Enumeration<ZipArchiveEntry> entries = zipInput.getEntries();
        while(entries.hasMoreElements()){
            ZipArchiveEntry zipEntry = entries.nextElement();
            if (!zipEntry.isDirectory()) {
                File entryFile = new File(unZipDir.getAbsolutePath() + File.separator + zipEntry.getName());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                byte[] buffer = new byte[1024];
                int len;
                InputStream in = zipInput.getInputStream(zipEntry);
                while ((len = in.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                bos.close();
                in.close();
            }
        }
    }

    /**
     * 解压并放回零时目录
     * @param zipFile 压缩文件夹
     * @return descDir
     */
    public static File unZipFiles(File zipFile) throws IOException {
        File tempDir = Files.createTempDirectory(UUID.randomUUID().toString()).toFile();
        unZipFiles(zipFile, tempDir);
        return tempDir;
    }

    /**
     * 文件压缩
     * @param out 压缩输出
     * @param fileMap 文件map集合，key为文件名，value是文件路径
     * @throws IOException 可能有IO异常
     */
    public static void zipFiles(ZipOutputStream out, Map<String, String> fileMap) throws IOException {
        for (String fileName : fileMap.keySet()) {
            String path = fileMap.get(fileName);
            toZipFiles(path, fileName, out);
        }
    }

    private static void toZipFiles(String file, String fileOriginName, ZipOutputStream zipOut) throws IOException {
        FileInputStream in = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileOriginName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = in.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        in.close();
    }

}
