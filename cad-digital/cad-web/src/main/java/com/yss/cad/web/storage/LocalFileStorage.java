package com.yss.cad.web.storage;

import com.yss.common.core.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:22
 */
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
}
