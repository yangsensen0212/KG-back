package com.yss.drill.transform;

import com.yss.drill.entity.DrillColumnCell;
import com.yss.drill.entity.ExcelCell;
import com.yss.drill.utils.MathOperate;
import com.yss.dxf.entity.GeometricPoint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Author 杨森森
 * @Data 2024/4/20  7:32
 * 钻孔柱状图中单元格转换为excel坐标
 */
public class ExcelCoordinateConverter {
    /**
     * cad（钻孔柱状图）中单元格的坐标转换为excel坐标
     * @param drillColumnCellList cad中的单元格列表
     * @return excel单元格列表
     */
    public static List<ExcelCell> convert(List<DrillColumnCell> drillColumnCellList) {
        // 注意：由于cad中y轴方向与excel是相反的，所以要对cad的y取反后进行平移与缩放
        drillColumnCellList.forEach(drillColumnCell -> drillColumnCell.getTopLeft().setY(drillColumnCell.getTopLeft().getY().negate()));
        drillColumnCellList.forEach(drillColumnCell -> drillColumnCell.getBottomRight().setY(drillColumnCell.getBottomRight().getY().negate()));
        Optional<DrillColumnCell> xMin = drillColumnCellList.stream().min(Comparator.comparing(o -> o.getTopLeft().getX()));
        Optional<DrillColumnCell> yMin = drillColumnCellList.stream().min(Comparator.comparing(o -> o.getTopLeft().getY()));
        // x平移量为xMin.getX(),y为yMin
        assert xMin.isPresent() && yMin.isPresent();
        double[] b = {xMin.get().getTopLeft().getX().doubleValue(), yMin.get().getTopLeft().getY().doubleValue()};
        //缩放
        double[] k = getK(drillColumnCellList);

        return getExcelCellList(drillColumnCellList, k, b);
    }

    static double[] getK(List<DrillColumnCell> drillColumnCellList) {
        double k1 = Double.MAX_VALUE;
        double k2 = Double.MAX_VALUE;
        for (DrillColumnCell drillColumnCell : drillColumnCellList) {
            GeometricPoint topLeft = drillColumnCell.getTopLeft();
            GeometricPoint bottomRight = drillColumnCell.getBottomRight();
            k1 = Math.min(MathOperate.subtract(bottomRight.getX(), topLeft.getX()), k1);
            k2 = Math.min(MathOperate.subtract(bottomRight.getY(), topLeft.getY()), k2);
        }
        return new double[] {k1, k2};
    }

    static List<ExcelCell> getExcelCellList(List<DrillColumnCell> drillColumnCellList, double[] k, double[] b) {
        List<ExcelCell> excelCellList = new ArrayList<>();
        drillColumnCellList.forEach(drillColumnCell -> {
            ExcelCell excelCell = new ExcelCell();
            GeometricPoint topLeft = drillColumnCell.getTopLeft();
            double x1 = topLeft.getX().doubleValue();
            double y1 = topLeft.getY().doubleValue();
            GeometricPoint bottomRight = drillColumnCell.getBottomRight();
            double x2 = bottomRight.getX().doubleValue();
            double y2 = bottomRight.getY().doubleValue();
            excelCell.setTextContent(drillColumnCell.getTextContent());
            excelCell.setTopLeft(createExcelPoint(x1, y1, k, b));
            excelCell.setBottomRight(createExcelPoint(x2, y2, k, b));
            excelCellList.add(excelCell);
        });
        return excelCellList;
    }

    static ExcelCell.ExcelPoint createExcelPoint(double x, double y, double[] k, double[] b) {
        return new ExcelCell.ExcelPoint((int) Math.round((y - b[1]) / k[1]), (int) Math.round((x - b[0]) / k[0]));
    }
}
