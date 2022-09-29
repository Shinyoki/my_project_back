package com.senko.framework.web.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysOperationLog;
import com.senko.common.core.vo.OperationLogVO;


/**
 * 操作日志Service
 *
 * @author senko
 * @date 2022/9/27 22:52
 */
public interface ISysOperationLogService extends IService<SysOperationLog> {

    /**
     * 查询操作日志列表
     *
     * @param logVO 请求参数
     */
    PageResult<SysOperationLog> listOperationLogDTOList(OperationLogVO logVO);

}
