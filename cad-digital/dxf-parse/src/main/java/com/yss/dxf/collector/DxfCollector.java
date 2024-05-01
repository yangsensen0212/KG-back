package com.yss.dxf.collector;

import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.continuity.IDxfText;
import com.yss.dxf.entity.*;

import java.util.List;
import java.util.Map;

public interface DxfCollector {

    /**
     * 获取dxf几何数据（初步）
     *
     * @return 返回map数据
     */
    Map<String, List<GeometricObject>> getMap();

    /**
     * 获取点线数据list
     * 将线段、多线的、圆等整合至一个列表
     * @return 点线数据
     */
    List<DxfLine> getDxfLineList();

    /**
     * 获取文本数据，包含文本和多文本
     *
     * @return 文本数据
     */
    List<IDxfText> getDxfTextList();


    /**
     * 获取dxf文件中所有的几何点
     *
     * @return 所有的几何点
     */
    List<GeometricPoint> getGeometricPointList();

    /**
     * 获取dxf文件中所有的几何线
     *
     * @return 所有的几何线
     */
    List<GeometricLine> getGeometricLineList();


    /**
     * 获取dxf文件中所有的几何弧线
     *
     * @return 所有的几何弧线
     */
    List<GeometricArc> getGeometricArcList();


    /**
     * 获取dxf文件中所有的几何圆
     *
     * @return 所有的几何圆
     */
    List<GeometricCircle> getGeometricCircleList();

    /**
     * 获取dxf文件中所有的几何多线段
     *
     * @return 所有的几何多线段
     */
    List<GeometricPolyLine> getGeometricPolyLineList();

    List<GeometricText> getGeometricTextList();

    List<GeometricMText> getGeometricMTextList();

}