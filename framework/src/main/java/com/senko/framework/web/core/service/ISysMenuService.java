package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.entity.SysMenu;
import com.senko.common.core.vo.RequestParamsVO;

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

    /**
     * 获取后台路由信息
     * @param requestParamsVO   请求参数
     * @return                  路由信息
     */
    List<SysMenusDTO> listBackMenus(RequestParamsVO requestParamsVO);

}
