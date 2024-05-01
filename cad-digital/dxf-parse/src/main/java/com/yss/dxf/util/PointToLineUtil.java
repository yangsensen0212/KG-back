package com.yss.dxf.util;

import com.google.common.collect.Lists;
import com.yss.dxf.entity.GeometricLine;
import com.yss.dxf.entity.GeometricPoint;

import java.util.List;

public class PointToLineUtil {

    /**
     * 将点集转换为线集的方法
     * @param pointList 点集
     * @return 线的集合
     */
    public static List<GeometricLine> pointTransformToLine(List<GeometricPoint> pointList) {
        List<GeometricLine> lineList = Lists.newArrayList();
        if (pointList != null && pointList.size()>1) {
            for (int i = 0; i < pointList.size() - 1; i++) {
                GeometricLine line = new GeometricLine(pointList.get(i), pointList.get(i + 1));
                lineList.add(line);
            }
        }
        return lineList;
    }
}
