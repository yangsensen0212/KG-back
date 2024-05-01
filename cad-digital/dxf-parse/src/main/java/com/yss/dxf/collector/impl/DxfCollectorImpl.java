package com.yss.dxf.collector.impl;

import com.yss.dxf.analysis.DxfAnalysis;
import com.yss.dxf.collector.DxfCollector;
import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.continuity.IDxfText;
import com.yss.dxf.entity.*;
import com.yss.dxf.enums.*;
import com.yss.dxf.util.DxfLineTransformUtil;
import com.yss.dxf.util.GeometricTransformUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description DXF数据采集者实现
 */
public class DxfCollectorImpl implements DxfCollector {

    /**
     * dxf文件
     */
    private final InputStream inputStream;

    /**
     * 初步解析的数据
     */
    private final Map<String, List<GeometricObject>> baseMap;

    /**
     * 构造方法
     *
     * @throws IOException IO异常
     */
    public DxfCollectorImpl(InputStream inputStream, String charSet) throws IOException {
        this.inputStream = inputStream;
        baseMap = getBaseMap(charSet);
    }

    /**
     * 获取dxf几何数据（初步）
     * map的key: 几何图像名称，比如：‘POINT’
     * map的value:几何图像集合，比如：List
     *
     * @return 返回map数据
     */
    @Override
    public Map<String, List<GeometricObject>> getMap() {
        return baseMap;
    }

    /**
     * 获取点线数据list，这种数据类型是符合rtas项目中使用的
     *
     * @return 点线数据
     */
    @Override
    public List<DxfLine> getDxfLineList() {
        //几何数据转换点线数据
        return DxfLineTransformUtil.geometricTransform(baseMap);
    }

    /**
     * 获取文本数据，包含文本和多文本
     *
     * @return 文本数据
     */
    @Override
    public List<IDxfText> getDxfTextList() {
        List<IDxfText> dxfTexts = new ArrayList<>();
        dxfTexts.addAll(getGeometricMTextList());
        dxfTexts.addAll(getGeometricTextList());
        return dxfTexts;
    }

    /**
     * 获取dxf文件中所有的几何点
     *
     * @return 返回几何点集合
     */
    @Override
    public List<GeometricPoint> getGeometricPointList() {
        //从map中把所有的GeometricPoint拿出来
        List<GeometricObject> objectList = baseMap.get(PointEnum.POINT_NAME.getCode());
        //把GeometricPoint转换为GeometricPoint
        return GeometricTransformUtil.transformGeometricPoint(objectList);
    }

    /**
     * 获取dxf文件中所有的几何线
     *
     * @return 返回几何线集合
     */
    @Override
    public List<GeometricLine> getGeometricLineList() {
        //从map中把所有的GeometricLine拿出来
        List<GeometricObject> objectList = baseMap.get(LineEnum.LINE_NAME.getCode());
        //把GeometricPoint转换为GeometricLine
        return GeometricTransformUtil.transformGeometricLine(objectList);
    }

    /**
     * 获取dxf文件中所有的几何弧线
     *
     * @return 返回几何弧线集合
     */
    @Override
    public List<GeometricArc> getGeometricArcList() {
        //从map中把所有的GeometricArc拿出来
        List<GeometricObject> objectList = baseMap.get(ArcEnum.ARC_NAME.getCode());
        //把GeometricPoint转换为GeometricArc
        return GeometricTransformUtil.transformGeometricArc(objectList);
    }

    /**
     * 获取dxf文件中所有的几何圆
     *
     * @return 返回几何圆集合
     */
    @Override
    public List<GeometricCircle> getGeometricCircleList() {
        //从map中把所有的GeometricCircle拿出来
        List<GeometricObject> objectList = baseMap.get(CircleEnum.CIRCLE_NAME.getCode());
        //把GeometricPoint转换为GeometricCircle
        return GeometricTransformUtil.transformGeometricCircle(objectList);
    }

    /**
     * 获取dxf文件中所有的几何多线段
     *
     * @return 返回几何多线段集合
     */
    @Override
    public List<GeometricPolyLine> getGeometricPolyLineList() {
        //从map中把所有的GeometricPolyLine拿出来
        List<GeometricObject> objectList = baseMap.get(PolyLineEnum.POLYLINE_NAME.getCode());
        //把GeometricPoint转换为GeometricPolyLine
        return GeometricTransformUtil.transformGeometricPolyLine(objectList);
    }

    @Override
    public List<GeometricText> getGeometricTextList() {
        //从map中把所有的GeometricText拿出来
        List<GeometricObject> objectList = baseMap.get(TextEnum.TEXT_NAME.getCode());
        //把GeometricPoint转换为GeometricText
        return GeometricTransformUtil.transformGeometricText(objectList);
    }

    @Override
    public List<GeometricMText> getGeometricMTextList() {
        //从map中把所有的GeometricText拿出来
        List<GeometricObject> objectList = baseMap.get(MTextEnum.MTEXT_NAME.getCode());
        //把GeometricObject转换为GeometricMText
        return GeometricTransformUtil.transformGeometricMText(objectList);
    }

    /**
     * 获取原始数据map
     *
     * @return 返回map数据
     * @throws IOException 文件IO异常
     */
    private Map<String, List<GeometricObject>> getBaseMap(String charSet) throws IOException {
        // 包装文件流 GBK StandardCharsets.UTF_8
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet));

        //读取文件获取数据，返回map
        Map<String, List<GeometricObject>> map = DxfAnalysis.getGeometricListMap(reader);

        //关闭文件流
        inputStream.close();
        reader.close();
        return map;
    }

}
