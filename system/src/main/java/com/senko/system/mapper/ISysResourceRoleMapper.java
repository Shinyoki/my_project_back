package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysResourceRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源 角色映射Mapper
 *
 * @author senko
 * @date 2022/9/16 20:54
 */
@Mapper
public interface ISysResourceRoleMapper extends BaseMapper<SysResourceRole> {
}
