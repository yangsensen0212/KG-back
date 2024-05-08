package com.yss.oauth;

import com.yss.common.user.domain.User;
import com.yss.common.user.domain.UserRole;
import com.yss.common.user.enums.Role;
import com.yss.common.user.service.IUserRoleService;
import com.yss.common.user.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @Author 杨森森
 * @Data 2024/4/11  13:17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
//    @Transactional
    public void insertUser() {
        User user = new User().setUserName("jinziyue").setPassword("123456").setRealName("靳子越");
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        User databaseUser = userService.getByUserName(user.getUserName());
        if (ObjectUtils.isEmpty(databaseUser)) {
            userService.save(user);
            UserRole role = new UserRole().setUserId(user.getId()).setRole(Role.ADMIN);
            userRoleService.save(role);
        }else {
            user.setId(databaseUser.getId());
            userService.updateById(user);
        }
    }
    @Test
    public void bcryptEncode() {
        String encode = passwordEncoder.encode("hkjhflkdsdf");
        System.out.println(encode);
    }
}
