package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.ResourceRoleDTO;
import com.senko.common.core.entity.SysResource;

import java.util.Set;

/**
 * 资源 服务
 *
 * @author senko
 * @date 2022/9/5 14:57
 */
public interface ISysResourceService extends IService<SysResource> {

    /**
     * 加载资源 角色 映射
     * （需认证的资源）
     */
    Set<ResourceRoleDTO> listNonAnonymousResourceRoles();

}
