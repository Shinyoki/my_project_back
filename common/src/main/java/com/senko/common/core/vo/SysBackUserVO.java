package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台用户VO
 *
 * @author senko
 * @date 2022/9/19 22:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("后台用户VO")
public class SysBackUserVO {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 用户状态 0:正常，1:禁用
     */
    @ApiModelProperty("用户状态 0:正常，1:禁用")
    private Integer isDisabled;

    /**
     * 用户角色ID
     */
    @ApiModelProperty("用户角色")
    private Long roleId;

}
