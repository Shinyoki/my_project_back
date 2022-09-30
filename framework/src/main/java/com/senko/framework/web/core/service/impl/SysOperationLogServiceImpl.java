package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysOperationLog;
import com.senko.common.core.vo.LogVO;
import com.senko.common.utils.page.PageUtils;
import com.senko.framework.web.core.service.ISysOperationLogService;
import com.senko.system.mapper.ISysOperationLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 操作日志
 *
 * @author senko
 * @date 2022/9/27 22:53
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<ISysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysOperationLogMapper operationLogMapper;

    /**
     * 查询操作日志列表
     *
     * @param logVO 请求参数
     */
    @Override
    public PageResult<SysOperationLog> listOperationLogDTOList(LogVO logVO) {

        boolean concatTimeCondition = Objects.nonNull(logVO.getStartTime()) && Objects.nonNull(logVO.getEndTime());
        // 把endDate的time改成 23:59:59
        Calendar calendar = null;
        Date endDate = logVO.getEndTime();
        if (Objects.nonNull(endDate)) {
            // 转换为 23:59:59
            calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            calendar.setTime(endDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            endDate = calendar.getTime();
        }

        Page<SysOperationLog> resultPage = operationLogMapper.selectPage(new Page<SysOperationLog>(PageUtils.getCurrent(), PageUtils.getSize()),
                new LambdaQueryWrapper<SysOperationLog>()
                        .like(Objects.nonNull(logVO.getUsername()), SysOperationLog::getUsername, logVO.getUsername())
                        .like(Objects.nonNull(logVO.getModule()), SysOperationLog::getModule, logVO.getModule())
                        .eq(Objects.nonNull(logVO.getStatus()), SysOperationLog::getStatus, logVO.getStatus())
                        .between(concatTimeCondition, SysOperationLog::getCreateTime, logVO.getStartTime(), endDate));

        return new PageResult<>((int)resultPage.getTotal(), resultPage.getRecords());
    }

}
