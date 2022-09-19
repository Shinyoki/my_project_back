package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户 角色 映射Mapper
 *
 * @author senko
 * @date 2022/9/4 21:56
 */
@Mapper
public interface ISysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Select("SELECT tua.id " +
            "FROM tb_user_auth tua " +
            "LEFT JOIN tb_user_role tur ON tur.user_id = tua.id " +
            "WHERE tur.id IS NULL")
    List<Long> listUnAssignedUserIds();

}
