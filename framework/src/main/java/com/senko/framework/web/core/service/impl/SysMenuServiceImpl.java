package com.senko.framework.web.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.constants.CommonConstants;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.SysMenu;
import com.senko.common.core.entity.SysMenuRole;
import com.senko.common.core.entity.SysRole;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.common.core.vo.SysMenuVO;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.exceptions.user.UserGetException;
import com.senko.common.utils.bean.BeanCopyUtils;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.config.security.manager.FilterInvocationSecurityMetadataSourceImpl;
import com.senko.framework.web.core.service.ISysMenuRoleService;
import com.senko.framework.web.core.service.ISysMenuService;
import com.senko.system.mapper.ISysMenuMapper;
import com.senko.system.mapper.ISysMenuRoleMapper;
import com.senko.system.mapper.ISysRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单Service
 *
 * @author senko
 * @date 2022/9/7 22:44
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<ISysMenuMapper, SysMenu> implements ISysMenuService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private ISysMenuMapper menuMapper;

    @Autowired
    private ISysMenuRoleMapper menuRoleMapper;

    @Autowired
    private ISysRoleMapper roleMapper;

    @Autowired
    private ISysMenuRoleService menuRoleService;

    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    /**
     * 菜单的DESC 比较器
     */
    public Comparator<SysMenusDTO> menusDESCComparator = (menu1, menu2) -> {

        if (menu1.getOrderNum() > menu2.getOrderNum()) {
            return 1;
        } else if (menu1.getOrderNum() < menu2.getOrderNum()) {
            return -1;
        } else {
            return 0;
        }

    };

    public Comparator<SysMenusDTO> menusASCComparator = (menu1, menu2) -> {

        if (menu1.getOrderNum() > menu2.getOrderNum()) {
            return -1;
        } else if (menu1.getOrderNum() < menu2.getOrderNum()) {
            return 1;
        } else {
            return 0;
        }

    };

    /**
     * 查询当前用户所能访问的菜单
     */
    @Override
    public List<SysMenusDTO> listMenusForUser(Long userId) {

        if (Objects.isNull(userId)) {
            throw new UserGetException("用户ID不能为空");
        }

        List<SysMenusDTO> rawMenus = null;
        if (Objects.equals(userId, CommonConstants.ADMIN_USER_ID)) {
            // 超级管理员
            rawMenus = menuMapper.listMenusForAdmin();
        } else {
            // 普通用户
            rawMenus = menuMapper.listMenusForCurUser(userId);
        }

        // 记录第一层菜单
        List<SysMenusDTO> topCategories = rawMenus.stream()
                // 是目录 并且 父ID为0或空
                .filter(menu -> menu.getParentId() == null || menu.getParentId() == 0)
                .collect(Collectors.toList());

        return buildSubmenus(rawMenus, topCategories).stream()
                .sorted(menusDESCComparator)
                .collect(Collectors.toList());

    }

    /**
     * 获取后台路由信息
     *
     * @param requestParamsVO 请求参数
     * @return 路由信息
     */
    @Override
    public List<SysMenusDTO> listBackMenus(RequestParamsVO requestParamsVO) {

        if (Objects.isNull(requestParamsVO)) {
            requestParamsVO = new RequestParamsVO();
        }
        return menuMapper.listBackMenus(requestParamsVO.getKeywords(), requestParamsVO.getIsHidden());

    }

    /**
     * 删除特定菜单
     * <p>
     * 删除目录也可以，但是为了省事，不做递归删除
     *
     * @param menuId 菜单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(Long menuId) {

        if (Objects.isNull(menuId)) {
            throw new ServiceException("菜单ID不能为空");
        }

        SysMenu sysMenu = menuMapper.selectById(menuId);

        if (Objects.nonNull(sysMenu)) {
            if (isCategory(sysMenu)) {

                // 是目录，则判断是否有子菜单
                List<SysMenu> subMenus = menuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getParentId, menuId));

                if (Objects.nonNull(subMenus) && subMenus.size() > 0) {
                    throw new ServiceException("目录下有子菜单，无法删除");
                }

            }

            // 不管是不是目录都删除
            List<Long> menuRoleList = menuRoleMapper
                    .selectList(new LambdaQueryWrapper<SysMenuRole>().eq(SysMenuRole::getMenuId, menuId))
                    .stream()
                    .map(SysMenuRole::getId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(menuRoleList)) {
                // 删除 菜单角色关联字段
                menuRoleMapper.deleteBatchIds(menuRoleList);
            }
            // 删除菜单
            menuMapper.deleteById(menuId);
            logger.info("用户:'{}' 删除了菜单:'{}'", SecurityUtils.getUserName(), sysMenu.getName());
            // 清除菜单缓存
            filterInvocationSecurityMetadataSource.clearResourceRolesCache();

        }

    }

    /**
     * 添加或更新菜单
     *
     * @param sysMenuVO 需要被添加或更新的菜单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(SysMenuVO sysMenuVO) {
        if (Objects.nonNull(sysMenuVO.getId())) {
            // 有自己的ID，说明是更新
            SysMenu sysMen = SysMenu.builder()
                    .id(sysMenuVO.getId())
                    .name(sysMenuVO.getName())
                    .parentId(sysMenuVO.getParentId())
                    .path(sysMenuVO.getPath())
                    .component(sysMenuVO.getComponent())
                    .icon(sysMenuVO.getIcon())
                    .menuType(sysMenuVO.getMenuType())
                    .isHidden(sysMenuVO.getIsHidden())
                    .orderNum(sysMenuVO.getOrderNum())
                    .build();

            // 更新tb_menu
            this.saveOrUpdate(sysMen);

            // 更新 菜单角色映射
            if (CollectionUtils.isNotEmpty(sysMenuVO.getRoles())) {
                // 删除原有的
                menuRoleMapper.delete(new LambdaQueryWrapper<SysMenuRole>()
                        .eq(SysMenuRole::getMenuId, sysMenuVO.getId()));
                // 添加新的
                List<SysMenuRole> menuRoleList = sysMenuVO.getRoles().stream()
                        .map(roleId -> SysMenuRole.builder()
                                .menuId(sysMenuVO.getId())
                                .roleId(roleId)
                                .build())
                        .collect(Collectors.toList());
                menuRoleService.saveBatch(menuRoleList);
            }


        } else {
            // 新增
            if (Objects.isNull(sysMenuVO.getParentId()) ||
                    sysMenuVO.getParentId() < 0) {
                sysMenuVO.setParentId(0L);
            }
            Optional.ofNullable(menuMapper.selectOne(new QueryWrapper<SysMenu>().eq("path", sysMenuVO.getPath())))
                    .ifPresent(menu -> {
                        throw new ServiceException("菜单路径已存在，不可重复添加");
                    });

            // 构建POJO并自动获得插入后的iD
            SysMenu newMenu = SysMenu.builder()
                    .menuType(sysMenuVO.getMenuType())
                    .name(sysMenuVO.getName())
                    .component(sysMenuVO.getComponent())
                    .isHidden(sysMenuVO.getIsHidden())
                    .icon(sysMenuVO.getIcon())
                    .orderNum(sysMenuVO.getOrderNum())
                    .path(sysMenuVO.getPath())
                    .parentId(sysMenuVO.getParentId())
                    .build();
            menuMapper.insert(newMenu);

            // 添加菜单角色关联
            if (CollectionUtils.isNotEmpty(sysMenuVO.getRoles())) {

                if (!sysMenuVO.getRoles().contains(CommonConstants.ADMIN_ROLE_ID)) {
                    sysMenuVO.getRoles().add(CommonConstants.ADMIN_ROLE_ID);
                }

                List<SysMenuRole> menuRoleList = sysMenuVO.getRoles().stream()
                        .map(roleId -> SysMenuRole.builder()
                                .menuId(newMenu.getId())
                                .roleId(roleId)
                                .build())
                        .collect(Collectors.toList());

                menuRoleService.saveBatch(menuRoleList);
            }
        }

        // 清除菜单缓存
        filterInvocationSecurityMetadataSource.clearResourceRolesCache();
    }

    /**
     * 是否为菜单
     */
    protected boolean isMenu(SysMenu sysMenu) {
        return Objects.nonNull(sysMenu) && Objects.equals(sysMenu.getMenuType(), 1);
    }

    /**
     * 是否为目录
     */
    protected boolean isCategory(SysMenu sysMenu) {
        return Objects.nonNull(sysMenu) && Objects.equals(sysMenu.getMenuType(), 0);
    }

    protected List<SysMenusDTO> findChildren(List<SysMenusDTO> allMenus, SysMenusDTO category) {

        return allMenus.stream()
                // 过滤菜单中的ParentId为当期的
                .filter(menu -> isParent(menu, category))
                // 递归
                .peek(menu -> {
                    if (isCategory(menu.getMenuType())) {
                        // 是目录，则继续找子菜单并赋值
                        menu.setChildren(findChildren(allMenus, menu));
                    } else {
                        // 不是目录，则直接赋值
                        menu.setChildren(new LinkedList<>());
                    }
                })
                .collect(Collectors.toList());

    }

    /**
     * 构建顶级父类的子类
     *
     * @param allMenus      所有菜单
     * @param topCategories 顶级父类;
     */
    protected List<SysMenusDTO> buildSubmenus(List<SysMenusDTO> allMenus, List<SysMenusDTO> topCategories) {

        List<SysMenusDTO> result = new LinkedList<>();
        for (SysMenusDTO topCategory : topCategories) {
            if (topCategory.getMenuType() == 0) {
                // 是目录，则设置子菜单
                topCategory.setChildren(findChildren(allMenus, topCategory));
            }
            result.add(topCategory);
        }
        return result;

    }


    /**
     * 是否 为目录
     */
    protected boolean isCategory(Integer menuType) {
        return null != menuType &&
                menuType == 0;
    }

    /**
     * 是否 存在父子关系
     */
    private boolean isParent(SysMenusDTO child, SysMenusDTO parent) {
        return child.getParentId() != null &&
                child.getParentId().equals(parent.getId());
    }


}
