package com.senko.common.core.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 后台路由DTO
 *
 * @author senko
 * @date 2022/9/7 22:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("后台路由DTO")
public class SysMenusDTO {

    /**
     * ID
     */
    @ApiModelProperty("菜单ID")
    private Long id;

    /**
     * 菜单名，如 首页
     */
    @ApiModelProperty("菜单名")
    private String name;

    /**
     * 菜单路径， 如 /
     */
    @ApiModelProperty("菜单地址")
    private String path;

    /**
     * 菜单所对应的Vue，如 /layout
     */
    @ApiModelProperty("菜单的组件地址")
    private String component;

    /**
     * 菜单的图标，如 el-icon-home
     */
    @ApiModelProperty("显示在SideBar的图标")
    private String icon;

    /**
     * 菜单的显示层级，越低越先显示 0 ~ 9
     */
    @ApiModelProperty("显示的优先级")
    private Integer orderNum;

    /**
     * 路由类型 0：目录（点了没反应，不需要进去）， 1：菜单
     */
    @ApiModelProperty("目录 or 菜单")
    private Integer menuType;

    /**
     * 父菜单ID
     *
     * 顶级目录会为null
     */
    @ApiModelProperty("父ID")
    private Long parentId;

    /**
     * 是否隐藏该菜单， 0：不隐藏，1：隐藏
     */
    @ApiModelProperty("是否隐藏")
    private Integer isHidden;

    /**
     * 子菜单/目录
     */
    @ApiModelProperty("子菜单")
    private List<SysMenusDTO> children;

    /**
     * 角色集合
     */
    @ApiModelProperty("角色集合")
    private Set<String> roles;

    /**
     * 创建时间
     */
    @ApiModelProperty
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty
    private LocalDateTime updateTime;

}
