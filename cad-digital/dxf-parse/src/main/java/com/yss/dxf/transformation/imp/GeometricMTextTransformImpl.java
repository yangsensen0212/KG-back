package com.yss.dxf.transformation.imp;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.entity.GeometricMText;
import com.yss.dxf.entity.GeometricObject;
import com.yss.dxf.transformation.DxfLineTransformation;
import com.yss.dxf.transformation.GeometricTransform;

import java.util.List;

/**
 * @Author 杨森森
 * @Data 2023/5/5  14:01
 */
public class GeometricMTextTransformImpl implements GeometricTransform<GeometricMText> {
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
                    geometricTransform = new GeometricMTextTransformImpl();
                }
            }
        }
        return geometricTransform;
    }
    /**
     * 将GeometricObject转换为GeometricText
     *
     * @param objectList 转换的数据
     * @return List
     */
    @Override
    public List<GeometricMText> transform(List<GeometricObject> objectList) {
        List<GeometricMText> textList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (GeometricObject object : objectList) {
                if (object instanceof GeometricMText) {
                    textList.add((GeometricMText) object);
                }
            }
        }
        return textList;
    }
}