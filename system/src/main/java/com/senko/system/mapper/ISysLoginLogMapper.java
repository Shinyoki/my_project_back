package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;


/**
 * 登录日志Mapper
 *
 * @author senko
 * @date 2022/9/29 22:49
 */
@Mapper
public interface ISysLoginLogMapper  extends BaseMapper<SysLoginLog> {

}

