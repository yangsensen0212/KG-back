package com.yss.cad;

import com.yss.cad.domain.DrillColumnCell;
import com.yss.cad.domain.ExcelCell;
import com.yss.cad.service.IDrillColumnService;
import com.yss.cad.utils.CoordinateConverter;
import com.yss.cad.utils.ExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/15  11:48
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DrillColumnTest {
    @Autowired
    private IDrillColumnService drillColumnService;

    @Test
    public void getCellListTest() throws IOException {
        File file = new File("C:\\Users\\pcforsen\\Downloads\\dxf\\2-1.dxf");
        List<DrillColumnCell> cellList = drillColumnService.getCellList(file);
        ExcelUtils.save(cellList, new FileOutputStream("C:\\Users\\pcforsen\\Downloads\\dxf\\2-1.xlsx"));
    }
}
