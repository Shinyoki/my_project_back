package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单VO
 *
 * @author senko
 * @date 2022/9/12 16:22
 */
@ApiModel("菜单VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysMenuVO {

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID",required = true)
    private Long id;

    /**
     * 父菜单ID
     * 从0开始
     */
    @ApiModelProperty(value = "父菜单ID", required = true)
    @NotNull(message = "父菜单ID不能为空")
    @DecimalMin(value = "0", message = "父菜单ID不能小于0")
    private Long parentId;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", required = true)
    private String icon;

    /**
     * 是否隐藏
     */
    @ApiModelProperty(value = "是否隐藏", required = true)
    @NotNull(message = "是否隐藏不能为空,0:显示，1：隐藏")
    @DecimalMin(value = "0", message = "是否隐藏不能小于0")
    @DecimalMax(value = "1", message = "是否隐藏不能大于1")
    private Integer isHidden;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序", required = true)
    @NotNull(message = "菜单排序不能为空，最低为0")
    @DecimalMin(value = "0", message = "菜单排序不能小于0")
    private Integer orderNum;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型 0:目录，1：菜单", required = true)
    @NotNull(message = "菜单类型不能为空")
    @DecimalMin(value = "0", message = "菜单类型不能小于0")
    @DecimalMax(value = "1", message = "菜单类型不能大于2")
    private Integer menuType;

    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径", required = true)
    @NotBlank(message = "菜单路径不能为空")
    private String path;

    /**
     * 路由组件
     */
    @ApiModelProperty(value = "路由组件", required = true)
    @NotBlank(message = "路由组件不能为空")
    private String component;

    /**
     * 菜单所属角色集合
     */
    @ApiModelProperty(value = "菜单所属角色集合", required = true)
    private List<Long> roles;

}
