package com.yss.drill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author 杨森森
 * @Data 2024/4/20  7:30
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

    public Integer getArea() {
        return (bottomRight.getRow() - topLeft.getRow()) * (bottomRight.getColumn() - topLeft.getColumn());
    }
}
