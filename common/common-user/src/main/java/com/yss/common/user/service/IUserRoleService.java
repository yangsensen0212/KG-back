package com.yss.common.user.service;

import com.yss.common.user.domain.UserRole;
import com.yss.common.mysql.service.IBaseService;

import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:12
 */
public interface IUserRoleService extends IBaseService<UserRole> {
    /**
     * 通过userId获取用户角色
     * @param userId 用户id
     * @return 用户角色
     */
    List<UserRole> getByUserId(Long userId);
}
