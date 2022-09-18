package com.senko.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权角色DTO
 *
 * @author senko
 * @date 2022/9/18 18:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("授权角色DTO")
public class SysUserAssignmentDTO {

    /**
     * 后台用户ID
     */
    @ApiModelProperty("后台用户ID")
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
     * 邮箱
     */
    @ApiModelProperty("邮箱地址")
    private String email;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 禁用状态
     */
    @ApiModelProperty("禁用状态")
    private String isDisabled;

}
