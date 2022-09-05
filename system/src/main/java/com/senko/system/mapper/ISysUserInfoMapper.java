package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息Mapper
 *
 * @author senko
 * @date 2022/9/3 20:10
 */
@Mapper
public interface ISysUserInfoMapper extends BaseMapper<SysUserInfo> {


}
