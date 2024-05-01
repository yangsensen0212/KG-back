package com.yss.oauth.service;

import com.yss.oauth.dto.UserLoginParamDto;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:30
 */
public interface ITokenService {
    OAuth2AccessToken login(UserLoginParamDto dto) throws Exception;
}
