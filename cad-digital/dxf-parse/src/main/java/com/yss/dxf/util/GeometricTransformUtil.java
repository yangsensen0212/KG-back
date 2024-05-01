package com.yss.dxf.util;

import com.yss.dxf.constant.EntityNameConstant;
import com.yss.dxf.entity.*;
import com.yss.dxf.transformation.GeometricTransform;
import com.yss.dxf.transformation.TransformBuilder;

import java.util.List;

public class GeometricTransformUtil {

    /**
     * 转换GeometricObject为GeometricPoint
     *
     * @param objectList 需要转换的GeometricObject数据
     * @return 转换GeometricPoint
     */
    public static List<GeometricPoint> transformGeometricPoint(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.POINT_NAME);
        return geometricTransform.transform(objectList);
    }

    /**
     * 转换GeometricObject为GeometricLine
     *
     * @param objectList 需要转换的GeometricObject数据
     * @return 转换GeometricLine
     */
    public static List<GeometricLine> transformGeometricLine(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.LINE_NAME);
        return geometricTransform.transform(objectList);
    }

    /**
     * 转换GeometricObject为GeometricArc
     *
     * @param objectList 需要转换的GeometricObject数据
     * @return 转换GeometricArc
     */
    public static List<GeometricArc> transformGeometricArc(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.ARC_NAME);
        return geometricTransform.transform(objectList);
    }

    /**
     * 转换GeometricObject为GeometricCircle
     *
     * @param objectList 需要转换的GeometricObject数据
     * @return 转换GeometricCircle
     */
    public static List<GeometricCircle> transformGeometricCircle(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.CIRCLE_NAME);
        return geometricTransform.transform(objectList);
    }


    /**
     * 转换GeometricObject为GeometricPolyLine
     *
     * @param objectList 需要转换的GeometricObject数据
     * @return 转换GeometricPolyLine
     */
    public static List<GeometricPolyLine> transformGeometricPolyLine(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.POLY_LINE_NAME);
        return geometricTransform.transform(objectList);
    }

    public static List<GeometricText> transformGeometricText(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.TEXT_NAME);
        return geometricTransform.transform(objectList);
    }

    public static List<GeometricMText> transformGeometricMText(List<GeometricObject> objectList) {
        GeometricTransform geometricTransform = TransformBuilder.builder(EntityNameConstant.MTEXT_NAME);
        return geometricTransform.transform(objectList);
    }
}
