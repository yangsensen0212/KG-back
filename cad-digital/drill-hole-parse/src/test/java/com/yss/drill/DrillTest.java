package com.yss.drill;

import com.yss.drill.entity.ParamConfigTest;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/4/20  10:26
 */
public class DrillTest {
    @Test
    public void toExcel() throws IOException {
        ParamConfigTest config = new ParamConfigTest();
        config.setStraightErr(0.01);
        config.setCosBetweenLineSegmentErr(0.01);
        config.setTextSortedErrRatio(0.1);
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\pcforsen\\Downloads\\test\\dxf\\9-1.dxf");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\pcforsen\\Downloads\\test\\dxf\\9-1.xlsx");
        Drill.build(fileInputStream, outputStream, "UTF-8", config);
        outputStream.close();
    }

    @Test
    public void testPoi() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\pcforsen\\Downloads\\test.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        int[] is = {1, 5, 3, 6, 4, 7};
        int[] js = {1, 5, 30, 60, 20, 70};
        for (int i : is) {
            for (int j : js) {
                XSSFRow row = sheet.createRow(i);
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(String.format("(%d, %d)", i, j));
            }
        }
        workbook.write(outputStream);
        outputStream.flush();
    }
}
