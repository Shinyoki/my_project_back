package com.senko.framework.web.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色服务
 *
 * @author senko
 * @date 2022/8/31 16:03
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 查询 被禁止的角色集合
     */
    List<String> listDisabledRoles();
    /**
     * 获取角色标签集合
     */
    List<SysRoleDTO> listRoleLabels();

    /**
     * 查询菜单的角色标签集合
     * @param menuId    菜单ID
     */
    List<SysRoleDTO> listMenuRoles(Long menuId);

    /**
     * 查询后台角色集合
     *
     * @param roleName   角色名称
     * @param roleLabel  角色标签
     * @param isDisabled 是否被禁用
     */
    PageResult<SysRoleDTO> listBackRoleList(String roleName, String roleLabel, Integer isDisabled);

    /**
     * 更新角色禁用状态
     * @param roleId           角色ID
     * @param isDisabled       禁用状态
     */
    void updateRoleIsDisabled(Long roleId, Integer isDisabled);

    /**
     * 批量删除角色
     * @param roleIds       角色ID集合
     */
    void deleteBathByIds(List<Long> roleIds);

}
