package com.senko.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 用户Mapper
 *
 * @author senko
 * @date 2022/8/31 13:48
 */
@Mapper
public interface ISysUserMapper extends BaseMapper<SysUser> {

}
