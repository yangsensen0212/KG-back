package com.yss.common.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yss.common.user.dao.IUserRoleDao;
import com.yss.common.user.domain.UserRole;
import com.yss.common.mysql.dao.ICrudDAO;
import com.yss.common.mysql.service.BaseServiceImpl;
import com.yss.common.user.service.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/11  11:13
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<IUserRoleDao, UserRole> implements IUserRoleService {
    /**
     * 通过userId获取用户角色
     *
     * @param userId 用户id
     * @return 用户角色
     */
    @Override
    public List<UserRole> getByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        return getBaseMapper().selectList(wrapper);
    }
}
