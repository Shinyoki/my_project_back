package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.entity.SysUserRole;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.framework.web.core.service.ISysUserRoleService;
import com.senko.system.mapper.ISysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 角色 映射Service
 *
 * @author senko
 * @date 2022/9/4 21:57
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<ISysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private ISysUserRoleMapper userRoleMapper;

    /**
     * 新增角色授权的用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID集合
     */
    @Override
    public void saveRoleAssignment(Long roleId, List<Long> userIds) {

        if (CollectionUtils.isEmpty(userIds)) {
            throw new ServiceException("用户ID集合不能为空");
        }
        // 假设这些用户都有相应的角色了，则先删除这些用户的角色
        userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .in(SysUserRole::getUserId, userIds));
        // 再新增这些用户的角色
        this.saveBatch(userIds.stream()
                .map(userId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList()));

    }

}
