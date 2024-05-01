package com.yss.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @Author 杨森森
 * @Data 2024/4/10  17:16
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private CustomClientDetails customClientDetails;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        List<String> authorizedGrantTypeList = customClientDetails.getAuthorizedGrantTypes();
        String[] authorizedGrantTypeArray = authorizedGrantTypeList.stream()
                .map(Object::toString)
                .toArray(String[]::new);
        List<String> scopeList = customClientDetails.getScopes();
        String[] scopeArray = scopeList.stream()
                .map(Object::toString)
                .toArray(String[]::new);
        clients.inMemory()
                .withClient(customClientDetails.getClientId())
                .secret(passwordEncoder.encode(customClientDetails.getSecret()))
                .authorizedGrantTypes(authorizedGrantTypeArray)
                .scopes(scopeArray);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(redisTokenStore());
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setAccessTokenValiditySeconds(customClientDetails.getAccessTokenValiditySeconds());
        tokenServices.setRefreshTokenValiditySeconds(customClientDetails.getRefreshTokenValiditySeconds());
        endpoints.tokenServices(tokenServices);
    }
}
