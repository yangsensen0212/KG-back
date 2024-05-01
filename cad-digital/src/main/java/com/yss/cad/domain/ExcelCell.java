package com.yss.cad.domain;

import com.yss.dxf.entity.GeometricPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author 杨森森
 * @Data 2024/4/15  15:24
 */
@Data
@Accessors(chain = true)
public class ExcelCell {
    private String textContent;
    private ExcelPoint topLeft;
    private ExcelPoint bottomRight;

    @Data
    @AllArgsConstructor
    public static class ExcelPoint {
        private Integer row;
        private Integer column;
    }
}
