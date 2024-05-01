package com.yss.common.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yss.common.mysql.domain.BaseEntity;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:25
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {
}

