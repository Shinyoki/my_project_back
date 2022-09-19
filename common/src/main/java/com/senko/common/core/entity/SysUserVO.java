package com.senko.common.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户VO
 *
 * @author senko
 * @date 2022/9/19 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户VO")
public class SysUserVO {

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
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 禁用状态 0-否 1-是
     */
    @ApiModelProperty("禁用状态，0：否，1：是")
    private Integer isDisabled;

}
