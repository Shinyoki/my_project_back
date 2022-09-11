package com.senko.framework.web.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.entity.SysMenu;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.common.utils.bean.BeanCopyUtils;
import com.senko.framework.web.core.service.ISysMenuService;
import com.senko.system.mapper.ISysMenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<SysMenusDTO> rawMenus = menuMapper.listMenusForCurUser(userId);

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
        List<SysMenu> sysMenus = menuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(Objects.nonNull(requestParamsVO.getIsHidden()), SysMenu::getIsHidden, requestParamsVO.getIsHidden())
                .like(StringUtils.isNotBlank(requestParamsVO.getKeywords()), SysMenu::getName, requestParamsVO.getKeywords()));
        return BeanCopyUtils.copyList(sysMenus, SysMenusDTO.class);
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
