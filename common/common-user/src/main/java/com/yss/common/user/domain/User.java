package com.yss.common.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import com.yss.common.mysql.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("`user`")
@NoArgsConstructor
public class User extends BaseEntity {
    @TableField
    private String userLogo;
    @NotNull
    @TableField
    private String userName; //登录名
    @NotNull
    @TableField
    private String realName; //真实姓名
    @NotNull
    @TableField("`password`")
    @JsonIgnore
    private String password;
    @TableField
    private String telephone;
    @TableField
    private String email;
    /**
     * 微信号
     */
    @TableField
    private String wechat;
    @TableField("`lock`")
    private Boolean lock = false; //是否锁定
}
