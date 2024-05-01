package com.yss.drill;

import com.yss.drill.analyse.DrillAnalyse;
import com.yss.drill.entity.IParamConfig;
import com.yss.drill.entity.ParamConfigTest;
import com.yss.drill.parse.impl.DrillParseImpl;
import com.yss.dxf.DXF;

import java.io.*;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:08
 */
public class Drill {
    public static OutputStream build(InputStream inputStream, OutputStream outputStream, String charSet, IParamConfig config) throws IOException {
        return new DrillParseImpl(inputStream, charSet, config, outputStream).getExcelOut();
    }
}
