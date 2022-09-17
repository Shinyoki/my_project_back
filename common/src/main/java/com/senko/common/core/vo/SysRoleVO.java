package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色VO
 *
 * @author senko
 * @date 2022/9/16 22:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("角色VO")
public class SysRoleVO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long id;

    /**
     * 角色名，如 管理员
     */
    @ApiModelProperty(value = "角色名",required = true)
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    /**
     * 角色标签，如 admin
     */
    @ApiModelProperty(value = "角色标签",required = true)
    @NotBlank(message = "角色标签不能为空")
    private String roleLabel;

    /**
     * 角色状态
     */
    @ApiModelProperty(value = "角色状态，0：正常，1：禁用", required = true)
    private Integer isDisabled;

    /**
     * 操作模式，1：查询，2：编辑，3：编辑角色可访菜单，4：编辑角色可操作资源
     */
    @ApiModelProperty(value = "操作模式，1：查询，2：编辑，3：编辑角色可访菜单，4：编辑角色可操作资源", required = true)
    private Integer operateMode;

    /**
     * 可访菜单ID集合
     */
    @ApiModelProperty(value = "可访菜单ID集合", required = true)
    private List<Long> checkedMenuIds;

    /**
     * 可访资源ID集合
     */
    @ApiModelProperty(value = "可访资源ID集合", required = true)
    private List<Long> checkedResourceIds;

}
