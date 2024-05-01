package com.yss.drill.transform;

import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.continuity.DxfPoint;
import com.yss.dxf.entity.GeometricLine;

import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/19  15:43
 */
public class DxfLineTransformGeo {
    /**
     * 必须是直线
     * @param dxfLine dxfLine
     * @return 几何直线，只包含两个点
     */
    public static GeometricLine transform(DxfLine dxfLine) {
        GeometricLine geometricLine = new GeometricLine();
        List<DxfPoint> pointList = dxfLine.getDataPointList();
        DxfPoint start = pointList.get(0);
        DxfPoint end = pointList.get(pointList.size() - 1);
        geometricLine.setStartX(start.getX());
        geometricLine.setStartY(start.getY());
        geometricLine.setEndX(end.getX());
        geometricLine.setEndY(end.getY());
        return geometricLine;
    }
}
