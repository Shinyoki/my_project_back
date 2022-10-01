package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author senko
 * @date 2022/10/1 18:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("更新密码VO")
public class UpdatePasswordVO {

    /**
     * 旧密码
     */
    @ApiModelProperty("旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    /**
     * 确认密码
     */
    @ApiModelProperty("确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

}
