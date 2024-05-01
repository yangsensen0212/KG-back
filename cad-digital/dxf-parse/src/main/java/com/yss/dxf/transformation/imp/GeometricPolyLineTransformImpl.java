package com.yss.dxf.transformation.imp;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.entity.GeometricObject;
import com.yss.dxf.entity.GeometricPolyLine;
import com.yss.dxf.transformation.DxfLineTransformation;
import com.yss.dxf.transformation.GeometricTransform;

import java.util.List;

public class GeometricPolyLineTransformImpl implements GeometricTransform<GeometricPolyLine> {

    /**
     * 实例对象
     */
    private static GeometricTransform geometricTransform;

    /**
     * 采用单例模式
     *
     * @return 返回实例对象
     */
    public static GeometricTransform getSingleInstance() {
        if (geometricTransform == null) {
            synchronized (DxfLineTransformation.class) {
                if (geometricTransform == null) {
                    geometricTransform = new GeometricPolyLineTransformImpl();
                }
            }
        }
        return geometricTransform;
    }

    /**
     * 几何多线段转换
     *
     * @param objectList 转换的数据
     * @return List<GeometricPolyLine>
     */
    @Override
    public List<GeometricPolyLine> transform(List<GeometricObject> objectList) {
        List<GeometricPolyLine> polyLineList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (GeometricObject object : objectList) {
                if (object instanceof GeometricPolyLine) {
                    polyLineList.add((GeometricPolyLine) object);
                }
            }
        }
        return polyLineList;
    }
}
