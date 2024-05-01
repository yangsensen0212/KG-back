package com.yss.dxf.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class GeometricPolyLine extends GeometricObject {

    private static final long serialVersionUID = 8069202425179988523L;

    /**
     * 顶点数量
     */
    private Integer vertexNum;

    /**
     * 多个顶点点组成
     */
    private List<GeometricVertex> vertexList;

}
