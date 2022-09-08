package com.senko.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 用户信息DTO
 *
 * @author senko
 * @date 2022/9/6 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户信息DTO")
public class LoginUserDTO {

    /**
     * 用户TOKEN
     */
    @ApiModelProperty("用户TOKEN")
    private String token;

    /**
     * 用户角色
     */
    @ApiModelProperty("角色")
    private Set<String> roles;

    /**
     * 用户昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

}
