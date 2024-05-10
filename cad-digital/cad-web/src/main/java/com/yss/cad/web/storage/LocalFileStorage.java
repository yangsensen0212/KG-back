package com.yss.cad.web.storage;

import com.yss.common.core.utils.FileUtils;
import com.yss.common.core.utils.ZipUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:22
 */
@Slf4j
public class LocalFileStorage implements IFileStorage{

    /**
     * 存储文件
     *
     * @param file 文件
     * @return 文件地址
     */
    @Override
    public String storage(File file) throws IOException {
        String fileName = file.getName();
        File destFile = File.createTempFile(fileName, FileUtils.getSuffix(fileName));
        FileChannel sourceChannel = new FileInputStream(file).getChannel();
        FileChannel destChannel = new FileOutputStream(destFile).getChannel();
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        return destFile.getPath();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件地址
     * @return 成功状态
     */
    @Override
    public boolean delete(String filePath) {
        log.info("deleting:{}", filePath);
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * 下载文件列表，压缩后下载
     *
     * @param name    压缩包文件名称
     * @param pathMap 路径集合
     * @return File
     */
    @Override
    public File download(String name, Map<String, String> pathMap) throws IOException {
        File tempFile = File.createTempFile(name, ".zip");
        FileOutputStream out = new FileOutputStream(tempFile);
        ZipOutputStream zipOut = new ZipOutputStream(out);
        ZipUtils.zipFiles(zipOut, pathMap);
        zipOut.close();
        out.close();
        return tempFile;
    }
}
