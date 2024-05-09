package com.yss.cad.web.storage;

import java.io.File;
import java.io.IOException;

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
}
