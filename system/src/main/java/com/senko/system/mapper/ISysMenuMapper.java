package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.entity.SysMenu;
import com.senko.common.core.vo.RequestParamsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenusDTO> listMenusForCurUser(@Param("userId") Long userId);

    /**
     * 为Admin展示所有菜单
     */
    List<SysMenusDTO> listMenusForAdmin();

    /**
     * 查询所有符合要求的后台菜单
     *
     * @param keywords 菜单名
     * @param isHidden 是否隐藏
     */
    List<SysMenusDTO> listBackMenus(@Param("keywords") String keywords, @Param("isHidden") Integer isHidden);

}
