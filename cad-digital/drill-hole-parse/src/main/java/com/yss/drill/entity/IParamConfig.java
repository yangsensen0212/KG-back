package com.yss.drill.entity;

/**
 * @Author 杨森森
 * @Data 2024/4/19  15:17
 */
public interface IParamConfig {
    /**
     * 获取直线与坐标轴的余弦误差
     * @return 误差
     */
    double getCosBetweenLineSegmentErr();
    double getStraightErr();
    double getTextSortedErrRatio();
}
