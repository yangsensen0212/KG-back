package com.yss.oauth.service.impl;

import com.yss.common.user.domain.User;
import com.yss.common.user.service.IUserService;
import com.yss.oauth.config.CustomClientDetails;
import com.yss.oauth.dto.UserLoginParamDto;
import com.yss.oauth.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:30
 */
@Service
public class TokenServiceImpl implements ITokenService {
    @Autowired
    private IUserService userService;
    @Autowired
    private CustomClientDetails customClientDetails;
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }




    @Override
    public OAuth2AccessToken login(UserLoginParamDto dto) throws Exception {
        User user = userService.getByUserName(dto.getUsername());
        if (ObjectUtils.isEmpty(user) || user.getLock()) {
            throw new Exception("用户为空或用户被锁定");
        }
        String client_secret = customClientDetails.getClientId() + ":" + customClientDetails.getSecret();
        client_secret = "Basic " + Base64.getEncoder().encodeToString(client_secret.getBytes(StandardCharsets.UTF_8));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", client_secret);
        List<String> scopeList = customClientDetails.getScopes();
        String scopes = String.join(",", scopeList);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(dto.getUsername()));
        map.put("password", Collections.singletonList(dto.getPassword()));
        map.put("grant_type", Collections.singletonList("password"));
        map.put("scope", Collections.singletonList(scopes));
        HttpEntity httpEntity = new HttpEntity(map, httpHeaders);
        return restTemplate
                .exchange(customClientDetails.getAccessTokenUri(), HttpMethod.POST, httpEntity, OAuth2AccessToken.class)
                .getBody();
    }

}
