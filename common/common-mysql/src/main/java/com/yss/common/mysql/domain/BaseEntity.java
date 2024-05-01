package com.yss.common.mysql.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 杨森森
 * @Data 2024/4/11  10:00
 */
@Data
public abstract class BaseEntity implements Serializable {
    //主键id
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class) //将long转换为string返回前端，避免精度丢失
    private Long id;
    //创建时间
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //逻辑删除
    @TableLogic(value = "0", delval = "1")
    @JsonIgnore
    private Integer deleted;
}
