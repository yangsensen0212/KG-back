package com.yss.cad.web.config;

import com.yss.drill.entity.IParamConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 杨森森
 * @Data 2024/5/1  21:27
 */
@Data
@Configuration
@ConfigurationProperties("drill-param")
public class DrillParamConfig implements IParamConfig {

    private double cosBetweenLineSegmentErr;

    private double straightErr;

    private double textSortedErrRatio;
}
