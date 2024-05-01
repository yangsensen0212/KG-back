package com.yss.common.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yss.common.user.dao.IUserDao;
import com.yss.common.user.domain.User;
import com.yss.common.mysql.service.BaseServiceImpl;
import com.yss.common.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:03
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<IUserDao, User> implements IUserService {
    /**
     * 通过userName获取用户
     *
     * @param userName 用户名
     * @return 用户
     */
    @Override
    public User getByUserName(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return getBaseMapper().selectOne(wrapper);
    }
}
