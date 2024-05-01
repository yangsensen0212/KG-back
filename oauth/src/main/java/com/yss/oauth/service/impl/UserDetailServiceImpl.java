package com.yss.oauth.service.impl;

import com.yss.common.user.domain.User;
import com.yss.common.user.domain.UserRole;
import com.yss.common.user.service.IUserRoleService;
import com.yss.common.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:15
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getByUserName(s);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("没有找到该用户名下的用户信息");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<UserRole> roleList = userRoleService.getByUserId(user.getId());
        for (UserRole role : roleList) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRole().getName());//spring Security中权限名称必须满足ROLE_XXX
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
