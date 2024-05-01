package com.yss.oauth.controller;

import com.yss.oauth.dto.UserLoginParamDto;
import com.yss.oauth.service.ITokenService;
import com.yss.oauth.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 杨森森
 * @Data 2024/4/11  15:45
 */
@RestController
@RequestMapping("token")
public class TokenController {
    @Autowired
    private ITokenService tokenService;

    @PostMapping("login")
    public OAuth2AccessToken login(UserLoginParamDto dto) throws Exception {
        return tokenService.login(dto);
    }
}
