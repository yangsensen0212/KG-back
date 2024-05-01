package com.yss.dxf.continuity;

import com.yss.dxf.entity.GeometricObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author 杨森森
 * @Data 2024/4/14  15:42
 */
public interface IDxfText {
    String getTextContent();
    /**
     * 中心点的x坐标
     */
    BigDecimal getX();

    /**
     * 中心点的y坐标
     */
    BigDecimal getY();

    /**
     * 中心点的z坐标
     */
    BigDecimal getZ();
}
