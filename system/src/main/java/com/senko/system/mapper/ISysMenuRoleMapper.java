package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysMenuRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单角色关联 Mapper
 *
 * @author senko
 * @date 2022/9/11 22:20
 */
@Mapper
public interface ISysMenuRoleMapper extends BaseMapper<SysMenuRole> {
}
