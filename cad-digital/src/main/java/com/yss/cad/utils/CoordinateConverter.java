package com.yss.cad.utils;

import com.yss.cad.domain.DrillColumnCell;
import com.yss.cad.domain.ExcelCell;
import com.yss.dxf.entity.GeometricPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Author 杨森森
 * @Data 2024/4/15  15:22
 * 坐标转换
 */
public class CoordinateConverter {
    /**
     * cad（钻孔柱状图）中单元格的坐标转换为excel坐标
     * @param drillColumnCellList cad中的单元格列表
     * @return excel单元格列表
     */
    public static List<ExcelCell> convertToExcelCoordinate(List<DrillColumnCell> drillColumnCellList) {
        // 注意：由于cad中y轴方向与excel是相反的，所以要对cad的y取反后进行平移与缩放
        drillColumnCellList.forEach(drillColumnCell -> drillColumnCell.getTopLeft().setY(drillColumnCell.getTopLeft().getY().negate()));
        drillColumnCellList.forEach(drillColumnCell -> drillColumnCell.getBottomRight().setY(drillColumnCell.getBottomRight().getY().negate()));
        Optional<DrillColumnCell> xMin = drillColumnCellList.stream().min(Comparator.comparing(o -> o.getTopLeft().getX()));
        Optional<DrillColumnCell> yMin = drillColumnCellList.stream().min(Comparator.comparing(o -> o.getTopLeft().getY()));
        // 寻找最小的单元格
        Optional<DrillColumnCell> minCell = drillColumnCellList.stream().min((o1, o2) -> {
            double x11 = o1.getTopLeft().getX().doubleValue();
            double y11 = o1.getTopLeft().getY().doubleValue();
            double x12 = o2.getBottomRight().getX().doubleValue();
            double y12 = o2.getBottomRight().getY().doubleValue();
            double x21 = o1.getTopLeft().getX().doubleValue();
            double y21 = o1.getTopLeft().getY().doubleValue();
            double x22 = o2.getBottomRight().getX().doubleValue();
            double y22 = o2.getBottomRight().getY().doubleValue();
            return Double.compare((x12 - x11) * (y12 - y11), (x22 - x21) * (y22 - y21));
        });
        // x平移量为xMin.getX(),y为yMin
        assert xMin.isPresent() && yMin.isPresent();
        double[] b = {xMin.get().getTopLeft().getX().doubleValue(), yMin.get().getTopLeft().getY().doubleValue()};
        //缩放
        assert minCell.isPresent();
        double[] k = {minCell.get().getBottomRight().getX().doubleValue() - minCell.get().getTopLeft().getX().doubleValue(), minCell.get().getBottomRight().getY().doubleValue() - minCell.get().getTopLeft().getY().doubleValue()};
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
