package com.senko.framework.web.core.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.dto.SysUserAssignmentDTO;
import com.senko.common.core.entity.SysMenuTree;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.dto.SysRoleMenuResourceDTO;
import com.senko.common.core.entity.*;
import com.senko.common.core.vo.RoleAssignmentVO;
import com.senko.common.core.vo.SysRoleVO;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.utils.bean.BeanCopyUtils;
import com.senko.common.utils.page.PageUtils;
import com.senko.framework.config.security.manager.AccessDecisionManagerImpl;
import com.senko.framework.web.core.service.ISysMenuRoleService;
import com.senko.framework.web.core.service.ISysResourceRoleService;
import com.senko.system.mapper.*;
import com.senko.framework.web.core.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author senko
 * @date 2022/8/31 16:04
 */
@Service
public class SysSysRoleServiceImpl extends ServiceImpl<ISysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMapper roleMapper;

    @Autowired
    private ISysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysMenuMapper menuMapper;

    @Autowired
    private ISysMenuRoleMapper menuRoleMapper;

    @Autowired
    private ISysResourceMapper resourceMapper;

    @Autowired
    private ISysResourceRoleMapper resourceRoleMapper;

    @Autowired
    private ISysResourceRoleService resourceRoleService;

    @Autowired
    private ISysMenuRoleService menuRoleService;

    @Autowired
    private AccessDecisionManagerImpl accessDecisionManager;

    @Autowired
    private ISysUserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(SysSysRoleServiceImpl.class);

    /**
     * 查询 被禁止的角色集合
     */
    @Override
    public List<String> listDisabledRoles() {

        return roleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                        .select(SysRole::getRoleLabel)
                        .eq(SysRole::getIsDisabled, 1))
                .stream()
                .map(SysRole::getRoleLabel)
                .collect(Collectors.toList());

    }

    /**
     * 查询菜单的角色标签集合
     *
     * @param menuId 菜单ID
     */
    @Override
    public List<SysRoleDTO> listMenuRoles(Long menuId) {
        // 查询菜单的角色标签集合
        return roleMapper.listMenuRoles(menuId);
    }

    /**
     * 查询后台角色集合
     *
     * @param roleName   角色名称
     * @param roleLabel  角色标签
     * @param isDisabled 是否被禁用
     */
    @Override
    public PageResult<SysRoleDTO> listBackRoleList(String roleName, String roleLabel, Integer isDisabled) {

        Page<SysRole> requestPage = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<SysRole> rolePage = roleMapper.selectPage(requestPage, new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(roleName), SysRole::getRoleName, roleName)
                .like(StringUtils.isNotBlank(roleLabel), SysRole::getRoleLabel, roleLabel)
                .eq(Objects.nonNull(isDisabled), SysRole::getIsDisabled, isDisabled));

        return new PageResult<>(((int) rolePage.getTotal()), BeanCopyUtils.copyList(rolePage.getRecords(), SysRoleDTO.class));
    }

    /**
     * 更新角色禁用状态
     *
     * @param roleId     角色ID
     * @param isDisabled 禁用状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleIsDisabled(Long roleId, Integer isDisabled) {
        roleMapper.updateById(SysRole.builder()
                .id(roleId)
                .isDisabled(isDisabled)
                .build());
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBathByIds(List<Long> roleIds) {
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>()
                .in(SysUserRole::getRoleId, roleIds));
        if (Objects.nonNull(count) && count > 0) {
            throw new ServiceException("角色已被用户绑定，无法删除");
        }
        accessDecisionManager.clearDisabledRoles();
        roleMapper.deleteBatchIds(roleIds);
    }

    /**
     * 获取角色的资源封装
     *
     * 为了方便，封装成只有一层深度的树
     * @param roleId 角色ID
     */
    @Override
    public SysRoleMenuResourceDTO listRoleBackResources(Long roleId) {

        // 所有资源
        List<SysResourceTree> rawResources = resourceMapper.selectList(new LambdaQueryWrapper<SysResource>()
                        .select(SysResource::getId, SysResource::getResourceName, SysResource::getParentId)
                ).stream()
                .map(sysResource -> SysResourceTree.builder()
                        .id(sysResource.getId())
                        .label(sysResource.getResourceName())
                        .parentId(sysResource.getParentId())
                        .build())
                .collect(Collectors.toList());

        // 父节点
        Set<Long> categoryIds = rawResources.stream()
                .filter(sysResourceTree -> sysResourceTree.getParentId() == null || sysResourceTree.getParentId() == 0)
                .map(SysResourceTree::getId)
                .collect(Collectors.toSet());

        // 角色拥有的资源 同时过滤掉父节点，防止element ui tree组件全选对应的子元素
        List<Long> checkedIds = resourceRoleMapper.selectList(new LambdaQueryWrapper<SysResourceRole>()
                        .select(SysResourceRole::getResourceId)
                        .eq(SysResourceRole::getRoleId, roleId)).stream()
                .map(SysResourceRole::getResourceId)
                .filter(id -> !categoryIds.contains(id))
                .collect(Collectors.toList());

        rawResources = buildResourceTree(rawResources);
        return SysRoleMenuResourceDTO.builder()
                .resourceTree(rawResources)
                .roleId(roleId)
                .checkedIds(checkedIds)
                .build();
    }

    /**
     * 新增或修改角色，包括其可访资源/菜单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(SysRoleVO roleVO) {
        if (Objects.isNull(roleVO.getId())) {
            // 新增
            SysRole entityToInsert = SysRole.builder()
                    .isDisabled(roleVO.getIsDisabled())
                    .roleName(roleVO.getRoleName())
                    .roleLabel(roleVO.getRoleLabel())
                    .build();
            roleMapper.insert(entityToInsert);
            // 新增角色资源关联
            if (CollectionUtils.isNotEmpty(roleVO.getCheckedResourceIds())) {
                List<SysResourceRole> resourceRoles = roleVO.getCheckedResourceIds().stream()
                        .map(resourceId -> SysResourceRole.builder()
                                .resourceId(resourceId)
                                .roleId(entityToInsert.getId())
                                .build())
                        .collect(Collectors.toList());
                // 批量插入
                resourceRoleService.saveBatch(resourceRoles);
            }
            // 新增角色菜单关联
            if (CollectionUtils.isNotEmpty(roleVO.getCheckedMenuIds())) {
                List<SysMenuRole> menuRoles = roleVO.getCheckedMenuIds().stream()
                        .map(menuId -> SysMenuRole.builder()
                                .menuId(menuId)
                                .roleId(entityToInsert.getId())
                                .build())
                        .collect(Collectors.toList());
                // 批量插入
                menuRoleService.saveBatch(menuRoles);
            }
        } else {
            // 修改
            SysRole entityToUpdate = SysRole.builder()
                    .id(roleVO.getId())
                    .isDisabled(roleVO.getIsDisabled())
                    .roleName(roleVO.getRoleName())
                    .roleLabel(roleVO.getRoleLabel())
                    .build();
            roleMapper.updateById(entityToUpdate);

            switch (roleVO.getOperateMode()) {
                case 3:
                    // 编辑角色可访菜单
                    // TODO 修改可访菜单顺序没效果
                    // 删除角色菜单关联
                    menuRoleMapper.delete(new LambdaQueryWrapper<SysMenuRole>()
                            .eq(SysMenuRole::getRoleId, entityToUpdate.getId()));
                    if (CollectionUtils.isNotEmpty(roleVO.getCheckedMenuIds())) {

                        // 新增角色菜单关联
                        List<SysMenuRole> menuRoles = roleVO.getCheckedMenuIds().stream()
                                .map(menuId -> SysMenuRole.builder()
                                        .menuId(menuId)
                                        .roleId(entityToUpdate.getId())
                                        .build())
                                .collect(Collectors.toList());
                        // 批量插入
                        menuRoleService.saveBatch(menuRoles);
                    }
                    break;
                case 4:
                    // 编辑角色可访资源
                    // 删除角色资源关联
                    resourceRoleMapper.delete(new LambdaQueryWrapper<SysResourceRole>()
                            .eq(SysResourceRole::getRoleId, entityToUpdate.getId()));
                    if (CollectionUtils.isNotEmpty(roleVO.getCheckedResourceIds())) {
                        // 新增角色资源关联
                        List<SysResourceRole> resourceRoles = roleVO.getCheckedResourceIds().stream()
                                .map(resourceId -> SysResourceRole.builder()
                                        .resourceId(resourceId)
                                        .roleId(entityToUpdate.getId())
                                        .build())
                                .collect(Collectors.toList());
                        // 批量插入
                        resourceRoleService.saveBatch(resourceRoles);
                    }
                    break;
            }

            accessDecisionManager.clearDisabledRoles();

        }
    }

    /**
     * 获取授权角色 集合
     *
     * @param assignmentVO 参数：角色ID，用户名、昵称、状态、、
     */
    @Override
    public PageResult<SysUserAssignmentDTO> listRoleAssignmentList(RoleAssignmentVO assignmentVO) {
        Optional.ofNullable(assignmentVO.getRoleId())
                .orElseThrow(() -> new ServiceException("未传入需要授权的角色ID"));
        List<Long> userIds = userRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                        .select(SysUserRole::getUserId)
                        .eq(SysUserRole::getRoleId, assignmentVO.getRoleId())).stream()
                .map(SysUserRole::getUserId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIds)) {
            return new PageResult<>(0, null);
        }
        // 存在用户
        List<SysUserAssignmentDTO> assignmentUserDTOS = userMapper.listAssignmentUserDTOs(userIds,
                PageUtils.getLimitFormatCurrent(),
                PageUtils.getSize(),
                assignmentVO.getUsername(),
                assignmentVO.getNickname(),
                assignmentVO.getEmail(),
                assignmentVO.getIsDisabled());
        return new PageResult<>(assignmentUserDTOS.size(), assignmentUserDTOS);
    }

    /**
     * 批量删除已授权的用户
     *
     * @param userIds 用户ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoleAssignment(Long roleId, List<Long> userIds) {
        logger.info("需要删除的用户ID集合：{}", JSON.toJSONString(userIds));
        userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, userIds));
    }

    /**
     * 查询未授权的用户
     *
     * @param assignmentVO 参数：角色ID，用户名、昵称、状态、、
     */
    @Override
    public PageResult<SysUserAssignmentDTO> listUnAssignmentUsers(RoleAssignmentVO assignmentVO) {
        /**
         * SELECT tua.id
         * FROM tb_user_auth tua
         * LEFT JOIN tb_user_role tur ON tur.user_id = tua.id
         * WHERE tur.id IS NULL
         */
        // 查询是否有还没有被指定角色的用户
        List<Long> unAssignmentUserIds = userRoleMapper.listUnAssignedUserIds();
        if (CollectionUtils.isEmpty(unAssignmentUserIds)) {
            return new PageResult<>(0, null);
        }

        // 查询对应用户
        List<SysUserAssignmentDTO> assignmentUserDTOS = userMapper.listAssignmentUserDTOs(unAssignmentUserIds,
                PageUtils.getLimitFormatCurrent(),
                PageUtils.getSize(),
                assignmentVO.getUsername(),
                assignmentVO.getNickname(),
                assignmentVO.getEmail(),
                assignmentVO.getIsDisabled());
        return new PageResult<>(assignmentUserDTOS.size(), assignmentUserDTOS);
    }

    /**
     * 获取该用户的角色信息
     *
     * @param userId 用户ID
     */
    @Override
    public SysRoleDTO getRoleByUserId(Long userId) {
        SysUserRole sysUserRole = userRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>()
                .select(SysUserRole::getRoleId)
                .eq(SysUserRole::getUserId, userId));
        if (Objects.isNull(sysUserRole)) {
            return null;
        }
        SysRole sysRole = roleMapper.selectById(sysUserRole.getRoleId());
        return BeanCopyUtils.copyObj(sysRole, SysRoleDTO.class);
    }

    private List<SysResourceTree> buildResourceTree(List<SysResourceTree> allResources) {
        ArrayList<SysResourceTree> returnList = new ArrayList<>();
        ArrayList<Long> temp = new ArrayList<>();

        for (SysResourceTree resourceTree : allResources) {
            temp.add(resourceTree.getId());
        }

        for (SysResourceTree resource : allResources) {
            if (!temp.contains(resource.getParentId())) {
                resourceRecursionFun(allResources, resource);
                returnList.add(resource);
            }
        }

        return returnList;
    }

    private void resourceRecursionFun(List<SysResourceTree> allResources, SysResourceTree curResource) {
        List<SysResourceTree> childList = getResourceChildList(allResources, curResource);
        curResource.setChildren(childList);
        for (SysResourceTree curChildResource : childList) {
            if (resourceHasChild(allResources, curChildResource)) {
                resourceRecursionFun(allResources, curChildResource);
            }
        }
    }

    private boolean resourceHasChild(List<SysResourceTree> allResources, SysResourceTree curChildResource) {
        return getResourceChildList(allResources, curChildResource).size() > 0;
    }

    private List<SysResourceTree> getResourceChildList(List<SysResourceTree> allResources, SysResourceTree curResource) {
        List<SysResourceTree> childList = new ArrayList<>();
        for (SysResourceTree resource : allResources) {
            if (Objects.equals(resource.getParentId(), curResource.getId())) {
                childList.add(resource);
            }
        }
        return childList;
    }

    private SysResourceTree findChildren(SysResourceTree resource, List<SysResourceTree> rawResources) {
        for (SysResourceTree it : rawResources) {
            if (resource.getId().equals(it.getParentId())) {
                if (resource.getChildren() == null) {
                    resource.setChildren(new ArrayList<>());
                }
                resource.getChildren().add(findChildren(it, rawResources));
            }
        }
        return resource;
    }

    // =====TODO 成功的树操作案例

    /**
     * 获取角色的菜单封装
     *
     * @param roleId 角色ID
     */
    @Override
    public SysRoleMenuResourceDTO listRoleBackMenus(Long roleId) {

        // 先查询所有菜单
        List<SysMenuTree> rawMenus = menuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                        .select(SysMenu::getId, SysMenu::getName, SysMenu::getParentId, SysMenu::getMenuType, SysMenu::getIsHidden)).stream()
                .map(sysMenu -> SysMenuTree.builder()
                        .id(sysMenu.getId())
                        .menuType(sysMenu.getMenuType())
                        .label(sysMenu.getName())
                        .parentId(sysMenu.getParentId())
                        .build())
                .collect(Collectors.toList());
        // 父id
        List<Long> parentIds = rawMenus.stream()
                .filter(sysMenuTree -> sysMenuTree.getParentId() != null)
                .map(SysMenuTree::getParentId)
                .collect(Collectors.toList());

        List<Long> checkedIds = menuRoleMapper.selectList(new LambdaQueryWrapper<SysMenuRole>()
                        .select(SysMenuRole::getMenuId, SysMenuRole::getRoleId)
                        .eq(SysMenuRole::getRoleId, roleId)).stream()
                .filter(sysMenuRole -> !parentIds.contains(sysMenuRole.getMenuId()))
                .map(SysMenuRole::getMenuId)
                .collect(Collectors.toList());
        rawMenus = buildMenuTree(rawMenus);

        return SysRoleMenuResourceDTO.builder()
                .menuTree(rawMenus)
                .roleId(roleId)
                .checkedIds(checkedIds)
                .build();
    }

    protected List<SysMenuTree> buildMenuTree(List<SysMenuTree> menus) {
        ArrayList<SysMenuTree> returnList = new ArrayList<>();
        ArrayList<Long> temp = new ArrayList<>();

        for (SysMenuTree menu : menus) {
            temp.add(menu.getId());
        }
        for (SysMenuTree menu : menus) {
            if (!temp.contains(menu.getParentId())) {
                // 如果父节点不存在，则menu为根节点
                // 递归获取并设置
                menuRecursionFunc(menus, menu);
                returnList.add(menu);
            }
        }

        return returnList;
    }

    /**
     * 递归以设置子节点
     */
    private void menuRecursionFunc(List<SysMenuTree> allMenus, SysMenuTree curElement) {
        // 得到子节点 列表
        List<SysMenuTree> childList = menuGetChildList(allMenus, curElement);
        // 给当前节点设置children
        curElement.setChildren(childList);
        // 重复递归当前节点的子节点
        for (SysMenuTree childCurElement : childList) {
            // 存在子节点就递归
            if (menuHasChild(allMenus, childCurElement)) {
                menuRecursionFunc(allMenus, childCurElement);
            }
        }
    }

    /**
     * 得到子节点 列表
     */
    private List<SysMenuTree> menuGetChildList(List<SysMenuTree> allMenus, SysMenuTree element) {
        ArrayList<SysMenuTree> returnList = new ArrayList<>();
        for (SysMenuTree curMenu : allMenus) {
            if (curMenu.getParentId().equals(element.getId())) {
                // 如果当前节点的父id为我们传入的节点的id，则为当前节点的子节点
                returnList.add(curMenu);
            }
        }
        return returnList;
    }

    /**
     * 是否存在子节点
     */
    private boolean menuHasChild(List<SysMenuTree> allMenus, SysMenuTree element) {
        return menuGetChildList(allMenus, element).size() > 0;
    }

    // ===============================================TODO 可得好好留着
    @Override
    public List<SysRoleDTO> listRoleLabels() {
        // 通过roleMapper查询所有role的label，并去重
        return roleMapper.selectList(new QueryWrapper<SysRole>()
                        .select("DISTINCT role_label, id"))
                .stream()
                .map(sysRole -> {
                    SysRoleDTO sysRoleDTO = new SysRoleDTO();
                    sysRoleDTO.setRoleLabel(sysRole.getRoleLabel());
                    sysRoleDTO.setId(sysRole.getId());
                    return sysRoleDTO;
                })
                .collect(Collectors.toList());
    }

}
