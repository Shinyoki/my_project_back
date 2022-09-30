package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.ResourceRoleDTO;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysResource;
import com.senko.common.core.vo.ResourceVO;

import java.util.List;
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

    /**
     * 获取资源的树形列表
     */
    List<SysResource> listResourceTree(ResourceVO resourceVO);

    /**
     * 更新资源的匿名访问状态
     */
    void updateAnonymousStatus(ResourceVO resourceVO, Integer isAnonymous);

    /**
     * 删除资源
     */
    void deleteResource(Long resourceId);

    /**
     * 保存或更新资源
     * @param resourceVO    资源VO
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

}
