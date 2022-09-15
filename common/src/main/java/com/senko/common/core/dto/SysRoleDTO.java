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
 * 角色DTO
 *
 * @author senko
 * @date 2022/9/12 10:22
 */
@ApiModel("角色DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long id;

    /**
     * 角色名称，如 管理员、普通用户
     */
    @ApiModelProperty("角色名称，如 管理员、普通用户")
    private String roleName;

    /**
     * 角色标签，如 admin、user
     */
    @ApiModelProperty("角色标签，如 admin、user")
    private String roleLabel;

    /**
     * 角色状态 0: 禁用 1: 启用
     */
    @ApiModelProperty("角色禁用状态，0: 禁用 1: 启用")
    private Integer isDisabled;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 资源ID列表
     */
    @ApiModelProperty("角色资源id列表")
    private List<Long> resourceIdList;

    /**
     * 角色菜单id列表
     */
    @ApiModelProperty("角色菜单id列表")
    private List<Long> menuIdList;

}
