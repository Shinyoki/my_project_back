package com.senko.framework.web.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.SysRole;
import com.senko.system.mapper.ISysRoleMapper;
import com.senko.framework.web.core.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author senko
 * @date 2022/8/31 16:04
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<ISysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMapper roleMapper;

    /**
     * 查询 被禁止的角色集合
     */
    @Override
    public List<String> listDisabledRoles() {

        return roleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                        .select(SysRole::getRoleLabel)
                        .eq(SysRole::getIsDisabled, 1))
                .stream()
                .map(SysRole::getRoleLabel)
                .collect(Collectors.toList());

    }

    /**
     * 查询菜单的角色标签集合
     * @param menuId    菜单ID
     */
    @Override
    public List<SysRoleDTO> listMenuRoles(Long menuId) {
        // 查询菜单的角色标签集合
        return roleMapper.listMenuRoles(menuId);
    }

    @Override
    public List<SysRoleDTO> listRoleLabels() {
        // 通过roleMapper查询所有role的label，并去重
        return roleMapper.selectList(new QueryWrapper<SysRole>()
                        .select("DISTINCT role_label, id"))
                .stream()
                .map(sysRole -> {
                    SysRoleDTO sysRoleDTO = new SysRoleDTO();
                    sysRoleDTO.setRoleLabel(sysRole.getRoleLabel());
                    sysRoleDTO.setId(sysRole.getId());
                    return sysRoleDTO;
                })
                .collect(Collectors.toList());
    }

}
