package com.yss.common.user.service;

import com.yss.common.user.domain.User;
import com.yss.common.mysql.service.IBaseService;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:02
 */
public interface IUserService extends IBaseService<User> {
    /**
     * 通过userName获取用户
     * @param userName 用户名
     * @return 用户
     */
    User getByUserName(String userName);
}
