package com.senko.framework.web.core.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.entity.SysRole;
import com.senko.system.mapper.ISysRoleMapper;
import com.senko.framework.web.core.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Override
    public Set<String> getRolesById(Long id) {
        // TODO 查询角色
        return null;
    }

}
