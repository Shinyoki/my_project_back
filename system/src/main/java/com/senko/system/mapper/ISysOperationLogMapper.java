package com.senko.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.senko.common.core.entity.SysOperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志Mapper
 *
 * @author senko
 * @date 2022/9/27 22:54
 */
@Mapper
public interface ISysOperationLogMapper extends BaseMapper<SysOperationLog> {

}
