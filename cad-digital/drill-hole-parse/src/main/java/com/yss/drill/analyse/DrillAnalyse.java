package com.yss.drill.analyse;

import com.yss.drill.entity.DrillColumnCell;
import com.yss.drill.entity.IParamConfig;
import com.yss.dxf.collector.DxfCollector;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:31
 */
@AllArgsConstructor
public class DrillAnalyse {
    private final IParamConfig config;
    private final DxfCollector collector;
    private final OutputStream excelOut;
    public void toExcel() throws IOException {
        List<DrillColumnCell> drillColumnCellList = new DrillCellAnalyse(collector, config).getDrillColumnCellList();
        ExcelAnalyse excelAnalyse = new ExcelAnalyse(drillColumnCellList, excelOut);
        excelAnalyse.save();
    }
}
