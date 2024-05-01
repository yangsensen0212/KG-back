package com.yss.common.mysql.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yss.common.mysql.dao.ICrudDAO;
import com.yss.common.mysql.domain.BaseEntity;

/**
 * 不要简化为BaseServiceImpl<T extends BaseEntity>会出现一些问题
 * 写成BaseServiceImpl<M extends ICrudDAO<T>  ,T extends BaseEntity>保证DAO能正确注入
 * @Author 杨森森
 * @Data 2024/4/11  10:26
 */
public abstract class BaseServiceImpl<M extends ICrudDAO<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {
}
