package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysLoginLog;
import com.senko.common.core.vo.LogVO;

/**
 * 登录日志 Service
 *
 * @author senko
 * @date 2022/9/11 22:24
 */
public interface ISysLoginLogService extends IService<SysLoginLog> {

    /**
     * 查询登录日志列表
     * @param logVO   请求参数
     */
    PageResult<SysLoginLog> listLoginLogDTOList(LogVO logVO);

}
