package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.entity.SysUserRole;

import java.util.List;

/**
 * 用户 角色 映射Service
 *
 * @author senko
 * @date 2022/9/4 21:57
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增角色授权的用户
     * @param roleId    角色ID
     * @param userIds   用户ID集合
     */
    void saveRoleAssignment(Long roleId, List<Long> userIds);
}
