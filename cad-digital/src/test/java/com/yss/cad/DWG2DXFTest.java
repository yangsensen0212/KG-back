package com.yss.cad;

import com.yss.cad.utils.DWG2DXF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/12  23:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DWG2DXFTest {
    @Autowired
    private DWG2DXF dwg2DXF;
    @Test
    public void dxf2dwgTest() throws IOException {
        String  dwgDir = "C:\\Users\\pcforsen\\Downloads\\test\\dwg";
        String  dxfDir = "C:\\Users\\pcforsen\\Downloads\\test\\dxf";
        dwg2DXF.transfer(dwgDir, dxfDir);
    }
}
