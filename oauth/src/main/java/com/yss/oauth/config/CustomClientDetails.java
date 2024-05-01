package com.yss.oauth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author 杨森森
 * @Data 2024/4/12  9:07
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.oauth2")
public class CustomClientDetails{
    private String clientId;
    private String secret;
    private List<String> authorizedGrantTypes;
    private List<String> scopes;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private String accessTokenUri;
}
