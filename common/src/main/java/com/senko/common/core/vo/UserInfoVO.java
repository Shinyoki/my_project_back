package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author senko
 * @date 2022/10/1 16:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户信息VO")
public class UserInfoVO {

    /**
     * 用户信息ID
     */
    @ApiModelProperty("用户信息ID")
    public Long id;

    /**
     * 用户ID，一般用不到
     */
    @ApiModelProperty("用户ID")
    public Long userId;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    public String nickname;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    public String avatar;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    public String email;

}
