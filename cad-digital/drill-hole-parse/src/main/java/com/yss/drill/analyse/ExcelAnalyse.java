package com.yss.drill.analyse;

import com.yss.drill.entity.DrillColumnCell;
import com.yss.drill.entity.ExcelCell;
import com.yss.drill.transform.ExcelCoordinateConverter;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @Author 杨森森
 * @Data 2024/4/20  7:28
 * 提供钻孔柱状图单元格转换为excel的能力
 */
@AllArgsConstructor
public class ExcelAnalyse {
    private List<DrillColumnCell> drillColumnCellList;
    private OutputStream fileOut;

    public void save() throws IOException {
        List<ExcelCell> excelCellList = ExcelCoordinateConverter.convert(drillColumnCellList);
//        排序防止多次调用sheet.createRow
        excelCellList.sort((o1, o2) -> {
            if (o1.getTopLeft().getRow().compareTo(o2.getTopLeft().getRow()) == 0) {
                return o1.getTopLeft().getColumn().compareTo(o2.getTopLeft().getColumn());
            }
            return o1.getTopLeft().getRow().compareTo(o2.getTopLeft().getRow());
        });
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        ExcelCell preCell = null;
        XSSFRow row = null;
        for (int i = 0; i < excelCellList.size(); i++) {
            ExcelCell cell = excelCellList.get(i);
            if(i==0 || !cell.getTopLeft().getRow().equals(preCell.getTopLeft().getRow())) {
                row = sheet.createRow(cell.getTopLeft().getRow());
            }
            XSSFCell xssfCell = row.createCell(cell.getTopLeft().getColumn());
            xssfCell.setCellValue(cell.getTextContent());
            preCell = cell;
        }
        mergeCell(sheet, excelCellList);
        workbook.write(fileOut);
    }

    /**
     * 合并单元格
     */
    public void mergeCell(XSSFSheet sheet, List<ExcelCell> excelCellList) {
        for (ExcelCell excelCell : excelCellList) {
            if (excelCell.getArea() > 1) {
                System.out.println(excelCell);
                ExcelCell.ExcelPoint topLeft = excelCell.getTopLeft();
                ExcelCell.ExcelPoint bottomRight = excelCell.getBottomRight();
                try {
                    sheet.addMergedRegion(new CellRangeAddress(topLeft.getRow(), bottomRight.getRow() - 1, topLeft.getColumn(), bottomRight.getColumn() -1));
                }catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
