package com.yss.drill.entity;

import com.yss.dxf.entity.GeometricPoint;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:42
 */
@Data
@Accessors(chain = true)
public class DrillColumnCell {
    private String textContent;
    private GeometricPoint topLeft;
    private GeometricPoint bottomRight;
}
