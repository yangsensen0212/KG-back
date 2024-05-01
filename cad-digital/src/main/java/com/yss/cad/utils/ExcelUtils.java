package com.yss.cad.utils;

import com.yss.cad.domain.DrillColumnCell;
import com.yss.cad.domain.ExcelCell;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Author 杨森森
 * @Data 2024/4/15  15:07
 */
public class ExcelUtils {
    public static void save(List<DrillColumnCell> drillColumnCellList, OutputStream fileOut) throws IOException {
        List<ExcelCell> excelCellList = CoordinateConverter.convertToExcelCoordinate(drillColumnCellList);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        for (ExcelCell excelCell : excelCellList) {
            ExcelCell.ExcelPoint topLeft = excelCell.getTopLeft();
            ExcelCell.ExcelPoint bottomRight = excelCell.getBottomRight();
            XSSFRow row = sheet.createRow(topLeft.getRow());
            XSSFCell cell = row.createCell(topLeft.getColumn());
            cell.setCellValue(excelCell.getTextContent());
            if (topLeft.getColumn() < bottomRight.getColumn() - 1 || topLeft.getRow() < bottomRight.getRow() - 1) {
                System.out.println(excelCell);
                try{
                    if (Objects.equals(topLeft.getRow(), bottomRight.getRow())){
                        sheet.addMergedRegion(new CellRangeAddress(topLeft.getRow(), bottomRight.getRow(), topLeft.getColumn(), bottomRight.getColumn() - 1));
                    }else if(Objects.equals(topLeft.getColumn(), bottomRight.getColumn())){
                        sheet.addMergedRegion(new CellRangeAddress(topLeft.getRow(), bottomRight.getRow() - 1, topLeft.getColumn(), bottomRight.getColumn()));
                    }else {
                        sheet.addMergedRegion(new CellRangeAddress(topLeft.getRow(), bottomRight.getRow() - 1, topLeft.getColumn(), bottomRight.getColumn() - 1));
                    }
                }catch (IllegalStateException e) {
                    e.printStackTrace();
                }


            }
        }
        workbook.write(fileOut);
    }
}
