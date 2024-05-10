package com.yss.cad.web.storage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:18
 */
public interface IFileStorage {
    /**
     * 存储文件
     * @param file 文件
     * @return 文件地址
     */
    String storage(File file) throws IOException;

    /**
     * 删除文件
     * @param filePath 文件地址
     * @return 成功状态
     */
    boolean delete(String filePath) throws IOException;

    /**
     * 下载文件列表，压缩后下载
     * @param name 压缩包文件名称
     * @param pathMap 路径集合
     * @return File
     */
    File download(String name, Map<String, String> pathMap) throws IOException;
}
