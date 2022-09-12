package com.senko.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户 & 用户信息
 *
 * @author senko
 * @date 2022/9/12 10:27
 */
@ApiModel("用户DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private String lastLoginTime;

    /**
     * 浏览器
     */
    @ApiModelProperty("浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String os;

    /**
     * IP
     */
    @ApiModelProperty("IP")
    private String ip;

    /**
     * 用户状态 0: 正常，1: 禁用
     */
    @ApiModelProperty("禁用状态, 0: 正常，1: 禁用")
    private Integer isDisabled;

    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private List<String> roles;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
