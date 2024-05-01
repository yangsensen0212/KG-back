package com.yss.dxf.transformation.imp;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.entity.GeometricObject;
import com.yss.dxf.entity.GeometricPoint;
import com.yss.dxf.transformation.DxfLineTransformation;
import com.yss.dxf.transformation.GeometricTransform;

import java.util.List;

public class GeometricPointTransformImpl implements GeometricTransform<GeometricPoint> {

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
                    geometricTransform = new GeometricPointTransformImpl();
                }
            }
        }
        return geometricTransform;
    }

    /**
     * 几何点的转换
     *
     * @param objectList 转换的数据
     * @return List<GeometricPoint>
     */
    @Override
    public List<GeometricPoint> transform(List<GeometricObject> objectList) {
        List<GeometricPoint> pointList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (GeometricObject object : objectList) {
                if (object instanceof GeometricPoint) {
                    pointList.add((GeometricPoint) object);
                }
            }
        }
        return pointList;
    }
}
