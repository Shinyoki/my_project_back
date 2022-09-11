package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单角色关联表
 *
 * @author senko
 * @date 2022/9/11 22:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_menu_role")
public class SysMenuRole {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 角色ID
     */
    private Long roleId;

}
