package com.senko.framework.web.core.service;


import com.senko.common.core.entity.SysLoginLog;
import com.senko.common.core.entity.SysOperationLog;
import com.senko.common.utils.bean.SpringUtils;
import com.senko.common.utils.ip.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * 异步处理日志记录
 *
 * @author senko
 * @date 2022/9/30 7:44
 */
public class AsyncLogService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLogService.class);

    /**
     * 存储 操作日志
     * @param entity  操作日志
     */
    public static TimerTask getOperationLogTask(SysOperationLog entity) {
        return new TimerTask() {
            @Override
            public void run() {
                String ip = entity.getIp();
                if (StringUtils.isNotBlank(ip)) {
                    try {
                        String s = SpringUtils.getBean(IpUtils.class).getCityInfo(ip).get();
                        entity.setLocation(s);
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error("获取用户IP归属地失败", e);
                    }
                }
                SpringUtils.getBean(ISysOperationLogService.class)
                        .save(entity);
            }
        };
    }

    /**
     * 存储 登录日志
     * @param entity    登录日志
     */
    public static TimerTask getLoginLogTask(SysLoginLog entity) {
        return new TimerTask() {
            @Override
            public void run() {
                String ip = entity.getIp();
                if (StringUtils.isNotBlank(ip)) {
                    try {
                        String s = SpringUtils.getBean(IpUtils.class).getCityInfo(ip).get();
                        entity.setLocation(s);
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error("获取用户IP归属地失败", e);
                    }
                }
                SpringUtils.getBean(ISysLoginLogService.class)
                        .save(entity);
            }
        };
    }

}
