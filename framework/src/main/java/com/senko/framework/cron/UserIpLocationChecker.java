package com.senko.framework.cron;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.senko.common.core.entity.SysUserInfo;
import com.senko.common.utils.ip.IpUtils;
import com.senko.framework.web.core.service.ISysUserInfoService;
import com.senko.system.mapper.ISysUserInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 定时更新用户IP归属地
 *
 * @author senko
 * @date 2022/9/19 16:35
 */
@Component
public class UserIpLocationChecker {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(UserIpLocationChecker.class);

    @Autowired
    private ISysUserInfoService userInfoService;

    @Autowired
    private ISysUserInfoMapper userInfoMapper;

    @Autowired
    private IpUtils ipUtils;


    // 每隔一小时更新一次用户Ip归属地
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void userIpLocationChecker() {
        // 整合为 角色Id和IP映射
        Map<Long, String> idAndIpMap = userInfoMapper.selectList(new LambdaQueryWrapper<SysUserInfo>()
                        .select(SysUserInfo::getId, SysUserInfo::getIp)).stream()
                .collect(Collectors.toMap(SysUserInfo::getId, SysUserInfo::getIp));

        // 获取所有用户的IP归属地
        List<SysUserInfo> userInfoList = idAndIpMap.entrySet().stream()
                .map(entry -> {
                    SysUserInfo userInfo = new SysUserInfo();
                    userInfo.setId(entry.getKey());
                    try {
                        if (StringUtils.isBlank(entry.getValue())) {
                            userInfo.setLocation("未知");
                        } else {
                            // 第一次get会发生阻塞，但之后的get由于调用异步的结果，不会阻塞
                            userInfo.setLocation(ipUtils.getCityInfo(entry.getValue()).get());
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error("获取用户IP归属地失败", e);
                        throw new RuntimeException(e);
                    }
                    return userInfo;
                })
                .collect(Collectors.toList());
        userInfoService.updateBatchById(userInfoList);
    }

}
