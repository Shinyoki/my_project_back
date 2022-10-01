package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.constants.CommonConstants;
import com.senko.common.core.dto.ResourceRoleDTO;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.SysResource;
import com.senko.common.core.entity.SysResourceRole;
import com.senko.common.core.entity.SysRole;
import com.senko.common.core.vo.ResourceVO;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.framework.config.security.manager.FilterInvocationSecurityMetadataSourceImpl;
import com.senko.framework.web.core.service.ISysResourceRoleService;
import com.senko.framework.web.core.service.ISysResourceService;
import com.senko.framework.web.core.service.ISysRoleService;
import com.senko.system.mapper.ISysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ISysResourceRoleService resourceRoleService;

    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Autowired
    private ISysRoleService roleService;

    @Override
    public Set<ResourceRoleDTO> listNonAnonymousResourceRoles() {
        return resourceMapper.listNonAnonymousResourceRoles();
    }

    /**
     * 获取资源的树形列表
     */
    @Override
    public List<SysResource> listResourceTree(ResourceVO resourceVO) {
        List<SysResource> rawResources = resourceMapper.selectList(new LambdaQueryWrapper<SysResource>()
                .select(SysResource::getId, SysResource::getResourceName,
                        SysResource::getParentId, SysResource::getIsAnonymous,
                        SysResource::getMethod, SysResource::getUrl,
                        SysResource::getResourceType)
                .like(Objects.nonNull(resourceVO.getResourceName()), SysResource::getResourceName, resourceVO.getResourceName())
                .like(Objects.nonNull(resourceVO.getUrl()), SysResource::getUrl, resourceVO.getUrl())
                .eq(Objects.nonNull(resourceVO.getIsAnonymous()), SysResource::getIsAnonymous, resourceVO.getIsAnonymous())
                .eq(Objects.nonNull(resourceVO.getMethod()), SysResource::getMethod, resourceVO.getMethod()));
        return buildResourceTree(rawResources);
    }

    /**
     * 更新资源的匿名访问状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAnonymousStatus(ResourceVO resourceVO, Integer isAnonymous) {

        if (Objects.isNull(resourceVO.getId())) {
            throw new ServiceException("资源id不能为空");
        }
        if (!isAnonymous.equals(CommonConstants.TRUE)) {
            isAnonymous = CommonConstants.FALSE;
        }
        SysResource entity = SysResource.builder()
                .id(resourceVO.getId())
                .isAnonymous(isAnonymous)
                .build();
        if (this.updateById(entity)) {
            // 更新资源权限
            filterInvocationSecurityMetadataSource.clearResourceRolesCache();
        }

    }

    /**
     * 删除资源
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResource(Long resourceId) {
        if (resourceMapper.selectCount(new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getParentId, resourceId)) > 0) {
            throw new ServiceException("该资源存在子资源，无法删除");
        }
        if (resourceMapper.deleteById(resourceId) > 0) {
            // 更新资源权限
            filterInvocationSecurityMetadataSource.clearResourceRolesCache();
            // 删除资源角色关联
            resourceRoleService.remove(new LambdaQueryWrapper<SysResourceRole>()
                    .eq(SysResourceRole::getResourceId, resourceId));
        }
    }

    /**
     * 保存或更新资源
     * @param resourceVO    资源VO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {

        if (resourceVO.getParentId() != 0) {
            SysResource sysResource = resourceMapper.selectById(resourceVO.getParentId());
            if (Objects.isNull(sysResource)) {
                throw new ServiceException("父资源不存在");
            }
            if (sysResource.getResourceType() == 1) {
                throw new ServiceException("父资源类型为接口，无法添加子资源");
            }
        }

        SysResource entity = new SysResource();
        entity.setId(resourceVO.getId());
        switch (resourceVO.getResourceType()) {

            case 0:
                // 模块
                entity.setParentId(resourceVO.getParentId());
                entity.setResourceName(resourceVO.getResourceName());
                entity.setIsAnonymous(resourceVO.getIsAnonymous());
                entity.setResourceType(0);
                break;

            case 1:
                // 接口
                entity.setParentId(resourceVO.getParentId());
                entity.setResourceName(resourceVO.getResourceName());
                entity.setIsAnonymous(resourceVO.getIsAnonymous());
                entity.setMethod(resourceVO.getMethod());
                entity.setUrl(resourceVO.getUrl());
                entity.setResourceType(1);
                break;

            default:
                throw new ServiceException("资源类型不正确");

        }

        if (this.saveOrUpdate(entity)) {
            List<Long> roleIds = resourceVO.getRoleIds();

            if (CollectionUtils.isNotEmpty(roleIds)) {
                if (!roleIds.contains(CommonConstants.ADMIN_ID)) {
                    roleIds.add(CommonConstants.ADMIN_ID);
                }
                Long resourceId = entity.getId();
                List<SysResourceRole> resourceRoleList = roleIds.stream()
                        .map(roleId -> SysResourceRole.builder()
                                .resourceId(resourceId)
                                .roleId(roleId)
                                .build())
                        .collect(Collectors.toList());
                // 删除原有的资源角色关联
                resourceRoleService.remove(new LambdaQueryWrapper<SysResourceRole>()
                        .eq(SysResourceRole::getResourceId, resourceId));
                // 保存新的资源角色关联
                resourceRoleService.saveBatch(resourceRoleList);
            }

            // 更新资源权限
            filterInvocationSecurityMetadataSource.clearResourceRolesCache();
        }

    }

    /**
     * 查询该资源的角色
     * @param resourceId    资源ID
     */
    @Override
    public List<SysRoleDTO> listResourceRoles(Long resourceId) {
        List<Long> roleIds = resourceRoleService.list(new LambdaQueryWrapper<SysResourceRole>()
                        .eq(SysResourceRole::getResourceId, resourceId))
                .stream()
                .map(SysResourceRole::getRoleId)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        return roleService.list(new LambdaQueryWrapper<SysRole>()
                        .select(SysRole::getId, SysRole::getRoleName, SysRole::getRoleLabel)
                        .in(SysRole::getId, roleIds))
                .stream()
                .map(sysRole -> SysRoleDTO.builder()
                        .id(sysRole.getId())
                        .roleName(sysRole.getRoleName())
                        .roleLabel(sysRole.getRoleLabel())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 为了简单，只做出一个深度的树
     */
    protected List<SysResource> buildResourceTree(List<SysResource> resources) {
        ArrayList<SysResource> returnList = new ArrayList<>();
        ArrayList<Long> temp = new ArrayList<>();

        for (SysResource sysResource : resources) {
            temp.add(sysResource.getId());
        }
        for (SysResource sysResource : resources) {
            if (!temp.contains(sysResource.getParentId())) {
                // 如果父节点不存在，则menu为根节点
                // 递归获取并设置
                resourceRecursionFunc(resources, sysResource);
                returnList.add(sysResource);
            }
        }

        return returnList;

    }

    private void resourceRecursionFunc(List<SysResource> resources, SysResource sysResource) {
        // 得到子节点 列表
        List<SysResource> childList = resourceGetChildList(resources, sysResource);
        // 给当前节点设置children
        sysResource.setChildren(childList);
        // 重复递归当前节点的子节点
        for (SysResource childCurElement : childList) {
            // 存在子节点就递归
            if (resourceHasChild(resources, childCurElement)) {
                resourceRecursionFunc(resources, childCurElement);
            }
        }
    }

    /**
     * 是否存在子节点
     */
    private boolean resourceHasChild(List<SysResource> allResources, SysResource element) {
        return resourceGetChildList(allResources, element).size() > 0;
    }

    /**
     * 得到子节点 列表
     */
    private List<SysResource> resourceGetChildList(List<SysResource> resources, SysResource element) {
        ArrayList<SysResource> returnList = new ArrayList<>();
        for (SysResource curResource : resources) {
            if (curResource.getParentId().equals(element.getId())) {
                // 如果当前节点的父id为我们传入的节点的id，则为当前节点的子节点
                returnList.add(curResource);
            }
        }
        return returnList;
    }

    /**
     * 是否为 顶级资源
     */
    protected boolean isTopResource(SysResource resource) {
        return resource.getParentId() == null || resource.getParentId() == 0;
    }

    private void handleChildren(List<SysResource> allResources, SysResource curElement) {
        List<SysResource> children = allResources.stream()
                .filter(resource -> resource.getParentId() != null && resource.getParentId().equals(curElement.getId()))
                .collect(Collectors.toList());
        curElement.setChildren(children);
    }

}
