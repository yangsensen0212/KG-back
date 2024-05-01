package com.yss.drill.utils;

import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.continuity.DxfPoint;
import com.yss.dxf.continuity.IDxfText;
import com.yss.dxf.entity.GeometricLine;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Author 杨森森
 * @Data 2024/4/19  15:08
 */
public class MathOperate {
    public static double subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b).doubleValue();
    }

    public static double subtract(BigDecimal a, Double b) {
        return a.doubleValue() - b;
    }

    public static BigDecimal subtractBigDecimal(BigDecimal a, Double b) {
        return a.subtract(new BigDecimal(b));
    }

    public static double cosBetweenLineSegment(GeometricLine line1, GeometricLine line2) {
        double x1 = subtract(line1.getStartX(), line1.getEndX());
        double y1 = subtract(line1.getStartY(), line1.getEndY());
        double x2 = subtract(line2.getStartX(), line2.getEndX());
        double y2 = subtract(line2.getStartY(), line2.getEndY());
        return (x1 * x2 + y1 * y2) / (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2));
    }

    /**
     * 计算某个点是否在直线所包含的x/y区间
     * @param text dxf文本
     * @param line dxf线段
     * @param axis 坐标类型 0为x方向，1为y方向
     * @return 是/否
     */
    public static boolean textInLineRange(IDxfText text, DxfLine line, int axis) {
        BigDecimal textCoord  = axis == 0? text.getX(): text.getY();
        List<DxfPoint> pointList = line.getDataPointList();
        pointList.sort((o1, o2) -> axis == 0 ? o1.getX().compareTo(o2.getX()) : o1.getY().compareTo(o2.getY()));
        int size = pointList.size();
        BigDecimal start = axis == 0 ? pointList.get(0).getX() : pointList.get(0).getY();
        BigDecimal end = axis == 0 ? pointList.get(size - 1).getX() : pointList.get(size - 1).getY();
        return textCoord.compareTo(start) > 0 && textCoord.compareTo(end) < 0;
    }

    /**
     * 文本到直线(该直线垂直于坐标轴)距离
     */
    public static double textToLineDistance(IDxfText text, DxfLine line) {
        double tx = text.getX().doubleValue();
        double ty = text.getY().doubleValue();
        List<DxfPoint> pointList = line.getDataPointList();
        DxfPoint point1 = pointList.get(0);
        DxfPoint point2 = pointList.get(pointList.size() - 1);
        double lx1 = point1.getX().doubleValue();
        double ly1 = point1.getY().doubleValue();
        double lx2 = point2.getX().doubleValue();
        double ly2 = point2.getY().doubleValue();
        if (lx1 == lx2) {
            //斜率无限大
            return Math.abs(lx1 - tx);
        }
        double k = (ly1 - ly2) / (lx1 - lx2);
        double b = ly1 - k * lx1;
        return Math.abs(k * tx - ty + b) / Math.sqrt(k * k + 1);
    }

    /**
     * 判断是否是直线
     * @param err 偏角误差
     * @return 是否
     */
    public static boolean isStraight(DxfLine line, double err) {
        List<DxfPoint> pointList = line.getDataPointList();
        for (int i = 2; i < pointList.size(); i++) {
            DxfPoint point1 = pointList.get(i);
            DxfPoint point2 = pointList.get(i);
            DxfPoint point3 = pointList.get(i);
            double x1 = point1.getX().doubleValue();
            double y1 = point1.getY().doubleValue();
            double x2 = point2.getX().doubleValue();
            double y2 = point2.getY().doubleValue();
            double x3 = point3.getX().doubleValue();
            double y3 = point3.getY().doubleValue();
            if (Math.abs(
                    ((x2 - x1) * (x3 - x1) + (y2 - y1) * (y3 - y1)) /
                            (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
                                    *
                                    Math.sqrt((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1)))
            )<=err) {
                return false;
            }
        }
        return true;
    }


    public static void textSorted(List<IDxfText> valueList, double ratio) {
//        由于cad中y坐标轴方向向上，与文字阅读顺序相反，所以，y方向降序排
//        valueList.sort(Comparator.comparing(IDxfText::getY).thenComparing(IDxfText::getX).reversed());
        Optional<IDxfText> max = valueList.stream().max(Comparator.comparing(IDxfText::getY));
        Optional<IDxfText> min = valueList.stream().min(Comparator.comparing(IDxfText::getY));
        assert max.isPresent() && min.isPresent();
       double err = ratio * subtract(max.get().getY(), min.get().getY());
        valueList.sort((o1, o2) -> {
            BigDecimal y1 = o1.getY();
            BigDecimal y2 = o2.getY();
            if (y1.compareTo(subtractBigDecimal(y2, -err)) < 0 && y1.compareTo(subtractBigDecimal(y2, err)) > 0) {
                return o1.getX().compareTo(o2.getX());
            }
            return y2.compareTo(y1);
        });
    }
}
