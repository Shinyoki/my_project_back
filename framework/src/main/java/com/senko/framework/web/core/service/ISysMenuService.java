package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单Service
 *
 * @author senko
 * @date 2022/9/7 22:44
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询当前用户所能访问的菜单
     */
    List<SysMenusDTO> listMenusForUser(Long userId);

}
