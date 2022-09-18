package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 授权角色VO
 *
 * @author senko
 * @date 2022/9/18 19:05
 */
@ApiModel("授权角色VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleAssignmentVO {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;

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
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 用户状态
     */
    @ApiModelProperty("用户状态")
    private Integer isDisabled;

}
