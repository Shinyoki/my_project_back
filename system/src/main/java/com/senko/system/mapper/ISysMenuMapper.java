package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 菜单Mapper
 *
 * @author senko
 * @date 2022/9/7 22:42
 */
@Mapper
public interface ISysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询当前用户所能访问的菜单
     * @param userId    用户ID
     * @return      菜单列表
     */
    List<SysMenusDTO> listMenusForCurUser(Long userId);

}
