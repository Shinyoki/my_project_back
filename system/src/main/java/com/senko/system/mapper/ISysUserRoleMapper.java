package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 角色 映射Mapper
 *
 * @author senko
 * @date 2022/9/4 21:56
 */
@Mapper
public interface ISysUserRoleMapper extends BaseMapper<SysUserRole> {
}
