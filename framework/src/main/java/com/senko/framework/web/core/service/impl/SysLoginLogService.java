package com.senko.framework.web.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysLoginLog;
import com.senko.common.core.vo.LogVO;
import com.senko.common.utils.page.PageUtils;
import com.senko.framework.web.core.service.ISysLoginLogService;
import com.senko.system.mapper.ISysLoginLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * 登录日志Service
 *
 * @author senko
 * @date 2022/9/29 22:51
 */
@Service
public class SysLoginLogService extends ServiceImpl<ISysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

    @Autowired
    private ISysLoginLogMapper loginLogMapper;

    /**
     * 查询登录日志列表
     * @param logVO   请求参数
     */
    @Override
    public PageResult<SysLoginLog> listLoginLogDTOList(LogVO logVO) {
        boolean conditionOfHasTimeLimit = Objects.nonNull(logVO.getStartTime()) && Objects.nonNull(logVO.getEndTime());
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

        Page<SysLoginLog> loginLogPage = loginLogMapper.selectPage(new Page<SysLoginLog>(PageUtils.getCurrent(), PageUtils.getSize()), new LambdaQueryWrapper<SysLoginLog>()
                .like(StringUtils.isNotBlank(logVO.getUsername()), SysLoginLog::getUsername, logVO.getUsername())
                .eq(Objects.nonNull(logVO.getStatus()), SysLoginLog::getStatus, logVO.getStatus())
                .between(conditionOfHasTimeLimit, SysLoginLog::getCreateTime, logVO.getStartTime(), endDate));
        return new PageResult<>(loginLogPage);
    }

}
