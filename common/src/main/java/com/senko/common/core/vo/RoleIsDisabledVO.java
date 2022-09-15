package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 更新角色禁用状态VO
 *
 * @author senko
 * @date 2022/9/15 22:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("更新角色禁用状态VO")
public class RoleIsDisabledVO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 是否被禁用，0：正常，1：禁用
     */
    @ApiModelProperty(value = "是否被禁用，0：正常，1：禁用", required = true)
    @NotNull(message = "是否被禁用不能为空")
    @Min(value = 0, message = "是否被禁用只能为0或1")
    @Max(value = 1, message = "是否被禁用只能为0或1")
    private Integer isDisabled;

}
