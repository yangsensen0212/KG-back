package com.yss.dxf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeometricVertex extends GeometricObject {

    private static final long serialVersionUID = 1685606296528470644L;

    /**
     * 顶点的x坐标
     */
    private BigDecimal x;

    /**
     * 顶点的y坐标
     */
    private BigDecimal y;

    /**
     * 顶点的z坐标
     */
    private BigDecimal z;
}
