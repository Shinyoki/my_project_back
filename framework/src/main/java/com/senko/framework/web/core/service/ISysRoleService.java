package com.senko.framework.web.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
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
    List<String> listRoleLabels();
}
