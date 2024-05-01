package com.yss.dxf;


import com.yss.dxf.collector.DxfCollector;
import com.yss.dxf.collector.impl.DxfCollectorImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DXF {

    /**
     * 上传文件、返回DXF数据采集者
     *
     * @return 数据采集者
     * @throws IOException io异常
     */
    public static DxfCollector build(InputStream inputStream, String charSet) throws IOException {
        return new DxfCollectorImpl(inputStream, charSet);
    }
}
