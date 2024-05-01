package com.yss.drill.entity;

import lombok.Data;

/**
 * @Author 杨森森
 * @Data 2024/4/19  16:29
 */
@Data
public class ParamConfigTest implements IParamConfig{

    private double cosBetweenLineSegmentErr;

    private double straightErr;

    private double textSortedErrRatio;
}
