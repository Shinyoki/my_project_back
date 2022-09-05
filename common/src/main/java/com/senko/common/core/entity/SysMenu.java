package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 菜单
 *
 * @author senko
 * @date 2022/8/31 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_menu")
public class SysMenu {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 菜单名，如 首页
     */
    private String name;

    /**
     * 菜单路径， 如 /
     */
    private String path;

    /**
     * 菜单所对应的Vue，如 /layout
     */
    private String component;

    /**
     * 菜单的图标，如 el-icon-home
     */
    private String icon;

    /**
     * 菜单的显示层级，越低越先显示 0 ~ 9
     */
    private Integer orderNum;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 是否隐藏该菜单， 0：不隐藏，1：隐藏
     */
    private Integer isHidden;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
