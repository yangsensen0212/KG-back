package com.yss.common.mysql.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 杨森森
 * @Data 2024/5/8  14:05
 */
@Configuration
public class DruidConfig {
    @Value("${spring.datasource.druid.login-username}")
    private String loginUsername;
    @Value("${spring.datasource.druid.login-password}")
    private String loginPassword;

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("loginUsername", loginUsername);
        registrationBean.addInitParameter("loginPassword", loginPassword);
        return registrationBean;
    }
}
