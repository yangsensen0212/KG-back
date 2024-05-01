package com.yss.dxf.transformation.imp;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.entity.GeometricArc;
import com.yss.dxf.entity.GeometricObject;
import com.yss.dxf.transformation.DxfLineTransformation;
import com.yss.dxf.transformation.GeometricTransform;

import java.util.List;

public class GeometricArcTransformImpl implements GeometricTransform<GeometricArc> {

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
                    geometricTransform = new GeometricArcTransformImpl();
                }
            }
        }
        return geometricTransform;
    }

    /**
     * 转换几何弧线线段
     *
     * @param objectList 转换的数据
     * @return 转换后的几何弧线线段
     */
    @Override
    public List<GeometricArc> transform(List<GeometricObject> objectList) {
        List<GeometricArc> arcList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (GeometricObject object : objectList) {
                if (object instanceof GeometricArc) {
                    arcList.add((GeometricArc) object);
                }
            }
        }
        return arcList;
    }
}
