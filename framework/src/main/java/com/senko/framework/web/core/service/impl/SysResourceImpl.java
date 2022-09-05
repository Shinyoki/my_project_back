package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.dto.ResourceRoleDTO;
import com.senko.common.core.entity.SysResource;
import com.senko.framework.web.core.service.ISysResourceService;
import com.senko.system.mapper.ISysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 资源 Service
 *
 * @author senko
 * @date 2022/9/5 15:07
 */
@Service
public class SysResourceImpl extends ServiceImpl<ISysResourceMapper, SysResource> implements ISysResourceService {

    @Autowired
    private ISysResourceMapper resourceMapper;

    @Override
    public Set<ResourceRoleDTO> listNonAnonymousResourceRoles() {
        return resourceMapper.listNonAnonymousResourceRoles();
    }

}
