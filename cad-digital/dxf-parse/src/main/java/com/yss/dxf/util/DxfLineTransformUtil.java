package com.yss.dxf.util;

import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.entity.GeometricObject;
import com.yss.dxf.enums.*;
import com.yss.dxf.transformation.DxfLineTransformation;
import com.yss.dxf.transformation.imp.DxfLineTransformationImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DxfLineTransformUtil {

    /**
     * 具体的进行转换的实现类
     */
    private static DxfLineTransformation transformation = new DxfLineTransformationImpl();

    /**
     * 几何数据转换为点线数据
     *
     * @param map 需要转换的map数据
     * @return 转换后的点线数据
     */
    public static List<DxfLine> geometricTransform(Map<String, List<GeometricObject>> map) {
        List<DxfLine> dxfLineList = new ArrayList<>();

        //将获取的几何数据转换为适配的点线数据
        List<DxfLine> arcList = transformation.arcTransform(map.get(ArcEnum.ARC_NAME.getCode()));
        List<DxfLine> circleList = transformation.circleTransform(map.get(CircleEnum.CIRCLE_NAME.getCode()));
        List<DxfLine> lineList = transformation.lineTransform(map.get(LineEnum.LINE_NAME.getCode()));
        List<DxfLine> pointList = transformation.pointTransform(map.get(PointEnum.POINT_NAME.getCode()));
        List<DxfLine> polyLineList = transformation.polyLineTransform(map.get(PolyLineEnum.POLYLINE_NAME.getCode()));

        //将获取的数据添加到一个总的集合中
        if (!CollectionUtils.isEmpty(arcList)) {
            dxfLineList.addAll(arcList);
        }
        if (!CollectionUtils.isEmpty(circleList)) {
            dxfLineList.addAll(circleList);
        }
        if (!CollectionUtils.isEmpty(lineList)) {
            dxfLineList.addAll(lineList);
        }
        if (!CollectionUtils.isEmpty(pointList)) {
            dxfLineList.addAll(pointList);
        }
        if (!CollectionUtils.isEmpty(polyLineList)) {
            dxfLineList.addAll(polyLineList);
        }
        return dxfLineList;
    }


}
