package com.yss.dxf.transformation.imp;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.entity.GeometricLine;
import com.yss.dxf.entity.GeometricObject;
import com.yss.dxf.transformation.DxfLineTransformation;
import com.yss.dxf.transformation.GeometricTransform;

import java.util.List;

public class GeometricLineTransformImpl implements GeometricTransform<GeometricLine> {

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
                    geometricTransform = new GeometricLineTransformImpl();
                }
            }
        }
        return geometricTransform;
    }

    /**
     * 几何线的转换
     *
     * @param objectList 转换的数据
     * @return List<GeometricLine>
     */
    @Override
    public List<GeometricLine> transform(List<GeometricObject> objectList) {
        List<GeometricLine> lineList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (GeometricObject object : objectList) {
                if (object instanceof GeometricLine) {
                    lineList.add((GeometricLine) object);
                }
            }
        }
        return lineList;
    }
}
