package com.senko.framework.web.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.entity.SysRole;

import java.util.Set;

/**
 * 角色服务
 *
 * @author senko
 * @date 2022/8/31 16:03
 */
public interface ISysRoleService extends IService<SysRole> {

    Set<String> getRolesById(Long id);
}
