package com.yss.dxf.transformation;

import com.yss.dxf.entity.GeometricObject;

import java.util.List;

public interface GeometricTransform<T> {

    /**
     * 将GeometricObject转换为T
     *
     * @param objectList 转换的数据
     * @return List
     */
    List<T> transform(List<GeometricObject> objectList);

}
