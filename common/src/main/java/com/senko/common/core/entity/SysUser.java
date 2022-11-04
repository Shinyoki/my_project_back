package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author senko
 * @date 2022/8/26 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_user_auth")
public class SysUser {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户信息ID
     */
    private Long userInfoId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 逻辑删除 0-未删除 1-已删除
     */
    private Integer isDelete;

    /**
     * 禁用状态，0: 正常，1: 禁用
     */
    private Integer isDisabled;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
