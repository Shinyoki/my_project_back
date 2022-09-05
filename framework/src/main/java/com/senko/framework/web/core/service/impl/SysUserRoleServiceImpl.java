package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.entity.SysUserRole;
import com.senko.framework.web.core.service.ISysUserRoleService;
import com.senko.system.mapper.ISysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * 用户 角色 映射Service
 *
 * @author senko
 * @date 2022/9/4 21:57
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<ISysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
}
