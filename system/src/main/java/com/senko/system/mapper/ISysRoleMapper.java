package com.senko.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 角色Mapper
 *
 * @author senko
 * @date 2022/8/31 16:04
 */
@Mapper
public interface ISysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取用户的角色
     * @param userId    用户id
     * @return      角色集合
     */
    Set<String> listUserRoles(@Param("userId") Long userId);

}
