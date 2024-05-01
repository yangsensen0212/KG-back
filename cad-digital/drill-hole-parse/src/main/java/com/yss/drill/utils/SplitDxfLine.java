package com.yss.drill.utils;

import com.yss.drill.transform.DxfLineTransformGeo;
import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.entity.GeometricLine;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:57
 * 将dxf中的直线按垂直于坐标轴划分，其它线段扔掉
 */
public class SplitDxfLine {
    /**
     * 划分
     * @param dxfLineList 原始线段
     * @param xLineList 划分后的于x轴平行的线
     * @param yLineList 划分后的于y轴平行的线
     * @param cosErr 余弦误差，即线段与坐标轴的余弦值误差
     * @param straightErr 是否是直线判断的误差
     */
    public static void split(List<DxfLine> dxfLineList, List<DxfLine> xLineList, List<DxfLine> yLineList, double cosErr, double straightErr) {
        for (DxfLine line : dxfLineList) {
            if (!MathOperate.isStraight(line, straightErr)) {
                continue;
            }
            GeometricLine geometricLine = DxfLineTransformGeo.transform(line);
            if (Math.abs(MathOperate.cosBetweenLineSegment(geometricLine, createGeometricLine(0., 0., 0., 0., 1., 0.))) <= cosErr) {
                // 平行于x轴
                xLineList.add(line);
            }else if(Math.abs(MathOperate.cosBetweenLineSegment(geometricLine, createGeometricLine(0., 0., 0., 1., 0., 0.))) <= cosErr) {
//              // 平行于y轴
                yLineList.add(line);
            }
        }
    }
    static GeometricLine createGeometricLine(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        return new GeometricLine(
                new BigDecimal(startX), new BigDecimal(startY), new BigDecimal(startZ), new BigDecimal(endX), new BigDecimal(endY), new BigDecimal(endZ)
        );
    }
}
