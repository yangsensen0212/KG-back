package com.yss.cad;

import com.yss.cad.mapper.DXFMapper;
import com.yss.dxf.collector.DxfCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/4/17  10:25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DXFParseTest {
    @Autowired
    private DXFMapper dxfMapper;
    @Test
    public void dxfParseTest() throws IOException {
        DxfCollector collector = dxfMapper.getCollector("C:\\Users\\pcforsen\\Downloads\\test\\dxf\\1-1.dxf");
        System.out.println(collector.getDxfTextList());
//        System.out.println(collector.getDxfLineList());
    }
}
