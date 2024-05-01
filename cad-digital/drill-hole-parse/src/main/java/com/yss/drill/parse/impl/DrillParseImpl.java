package com.yss.drill.parse.impl;

import com.yss.drill.analyse.DrillAnalyse;
import com.yss.drill.entity.IParamConfig;
import com.yss.drill.parse.IDrillParse;
import com.yss.dxf.DXF;
import com.yss.dxf.collector.DxfCollector;

import java.io.*;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:18
 */
public class DrillParseImpl implements IDrillParse {
    private final InputStream inputStream;
    private final OutputStream excelOut;
    private final IParamConfig config;
    public DrillParseImpl(InputStream inputStream, String charset, IParamConfig config, OutputStream excelOut) throws IOException {
        this.inputStream = inputStream;
        this.config = config;
        this.excelOut = excelOut;
        startParse(charset);
    }


    /**
     * 获取解析后的excel
     *
     * @return excel文件流
     */
    @Override
    public OutputStream getExcelOut() {
        return excelOut;
    }


    private void startParse(String charset) throws IOException {
        DxfCollector collector = DXF.build(inputStream, charset);
        new DrillAnalyse(config, collector, excelOut).toExcel();
    }

}
