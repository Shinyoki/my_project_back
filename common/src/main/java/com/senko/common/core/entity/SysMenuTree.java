package com.senko.common.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单树封装
 *
 * @author senko
 * @date 2022/9/16 15:02
 */
@ApiModel("菜单树封装")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysMenuTree {

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private Long id;

    /**
     * 菜单名
     */
    @ApiModelProperty(value = "菜单名")
    private String label;

    /**
     * 菜单类型 0:目录 1:菜单
     */
    @ApiModelProperty(value = "菜单类型 0:目录 1:菜单 ")
    private Integer menuType;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    private List<SysMenuTree> children;

}
