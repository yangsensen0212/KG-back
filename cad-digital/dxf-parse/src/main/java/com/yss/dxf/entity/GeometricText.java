package com.yss.dxf.entity;

import com.yss.dxf.continuity.IDxfText;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author 杨森森
 * @Data 2023/5/5  9:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class GeometricText extends GeometricObject implements IDxfText {
    /**
     * 文本内容
     */
    private String textContent;
    /**
     * 中心点的x坐标
     */
    private BigDecimal x;

    /**
     * 中心点的y坐标
     */
    private BigDecimal y;

    /**
     * 中心点的z坐标
     */
    private BigDecimal z;
}
