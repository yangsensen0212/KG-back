package com.yss.common.mysql.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.common.mysql.domain.BaseEntity;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:23
 */
public interface ICrudDAO<T extends BaseEntity> extends BaseMapper<T> {
}
