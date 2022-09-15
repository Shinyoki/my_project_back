package com.senko.framework.web.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysRole;
import com.senko.common.core.entity.SysUserRole;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.utils.bean.BeanCopyUtils;
import com.senko.common.utils.page.PageUtils;
import com.senko.system.mapper.ISysRoleMapper;
import com.senko.framework.web.core.service.ISysRoleService;
import com.senko.system.mapper.ISysUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ISysUserRoleMapper userRoleMapper;

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

    /**
     * 查询后台角色集合
     *
     * @param roleName   角色名称
     * @param roleLabel  角色标签
     * @param isDisabled 是否被禁用
     */
    @Override
    public PageResult<SysRoleDTO> listBackRoleList(String roleName, String roleLabel, Integer isDisabled) {

        Page<SysRole> requestPage = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<SysRole> rolePage = roleMapper.selectPage(requestPage, new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(roleName), SysRole::getRoleName, roleName)
                .like(StringUtils.isNotBlank(roleLabel), SysRole::getRoleLabel, roleLabel)
                .eq(Objects.nonNull(isDisabled), SysRole::getIsDisabled, isDisabled));

        return new PageResult<>(((int) rolePage.getTotal()), BeanCopyUtils.copyList(rolePage.getRecords(), SysRoleDTO.class));
    }

    /**
     * 更新角色禁用状态
     * @param roleId           角色ID
     * @param isDisabled       禁用状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleIsDisabled(Long roleId, Integer isDisabled) {
        roleMapper.updateById(SysRole.builder()
                .id(roleId)
                .isDisabled(isDisabled)
                .build());
    }

    /**
     * 批量删除角色
     * @param roleIds       角色ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBathByIds(List<Long> roleIds) {
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>()
                .in(SysUserRole::getRoleId, roleIds));
        if (Objects.nonNull(count) && count > 0)  {
            throw new ServiceException("角色已被用户绑定，无法删除");
        }
        roleMapper.deleteBatchIds(roleIds);
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
