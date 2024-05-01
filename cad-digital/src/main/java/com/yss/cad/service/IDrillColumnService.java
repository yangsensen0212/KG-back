package com.yss.cad.service;

import com.yss.cad.domain.DrillColumnCell;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/13  22:29
 */
public interface IDrillColumnService {
    /**
     * 提取信息成excel
     * @param dxfDir cad文件目录
     * @return 转换后的excel文件目录
     */
    File toExcelBatch(File dxfDir) throws IOException;
    /**
     * 提取信息成excel
     * @param dxfFile cad文件
     * @return 转换后的excel文件目录
     */
    File toExcel(File dxfFile) throws IOException;

    /**
     * 解析cad钻孔柱状图，得到单元格数据
     * @param dxfFile dxf文件
     * @return 解析后的单元格数据
     */
    List<DrillColumnCell> getCellList(File dxfFile) throws IOException;

}
