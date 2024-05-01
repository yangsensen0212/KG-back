package com.yss.common.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import com.yss.common.user.enums.Role;
import com.yss.common.mysql.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_role")
@Accessors(chain = true)
@NoArgsConstructor
public class UserRole extends BaseEntity {
    @TableField
    @NotNull
    private Long userId;
    @TableField
    @NotNull
    private Role role;
}

