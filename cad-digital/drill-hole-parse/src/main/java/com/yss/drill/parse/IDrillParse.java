package com.yss.drill.parse;

import java.io.OutputStream;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:11
 */
public interface IDrillParse {
    /**
     * 获取解析后的excel
     * @return excel文件流
     */
    OutputStream getExcelOut();
}
