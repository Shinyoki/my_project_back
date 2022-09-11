package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.entity.SysMenuRole;
import com.senko.framework.web.core.service.ISysMenuRoleService;
import com.senko.system.mapper.ISysMenuRoleMapper;
import org.springframework.stereotype.Service;

/**
 * 菜单角色关联 Service
 *
 * @author senko
 * @date 2022/9/11 22:24
 */
@Service
public class SysMenuRoleServiceImpl extends ServiceImpl<ISysMenuRoleMapper, SysMenuRole> implements ISysMenuRoleService {

}
