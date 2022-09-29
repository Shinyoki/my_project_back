package com.senko.framework.web.service;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.senko.common.annotations.LogOperation;
import com.senko.common.constants.CommonConstants;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysOperationLog;
import com.senko.common.utils.http.ServletUtils;
import com.senko.common.utils.ip.IpUtils;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.system.mapper.ISysOperationLogMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 异步Service
 *
 * 单独抽出为一个Service再调用，防止Async注解失效
 * @author senko
 * @date 2022/9/29 19:43
 */
@Service
public class AsyncOperationLogService {

    @Autowired
    private ISysOperationLogMapper operationLogMapper;

    /**
     * 记录 操作日志
     * @param joinPoint 切点
     * @param logEnum   日志枚举
     * @param returnVal 返回值
     * @param exception 异常
     */
    @Async
    public void recordOperationLog(JoinPoint joinPoint, LogOperation logEnum, Object returnVal, Exception exception) {
        // 获取请求信息
        ServletRequestAttributes requestAttributes = ServletUtils.getRequestAttributes();
        // 方法 & 类
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> topClass = method.getDeclaringClass();

        // 获取类和方法上的注解
        Api api = topClass.getAnnotation(Api.class);
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        LogOperation logOperation = method.getAnnotation(LogOperation.class);

        LoginUser loginUser = SecurityUtils.getLoginUserIfHasLogin();

        // 实体类
        SysOperationLog entity = new SysOperationLog();

        // 用户相关
        if (Objects.nonNull(loginUser)) {
            entity.setUserId(loginUser.getId());
            entity.setUsername(loginUser.getUsername());
        }
        entity.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        // 接口相关
        entity.setMethod(method.getName());
        // 拼接方法的类路径和方法名
        entity.setHandler(topClass.getName() + "#" + method.getName());
        entity.setUrl(ServletUtils.getRequest().getRequestURI());
        entity.setModule(api.tags()[0]);
        entity.setType(logOperation.value().getMethod());
        entity.setDes(annotation.value());
        entity.setParams(requestAttributes.getRequest().getQueryString());
        // 操作环境
        UserAgent userAgent = UserAgentUtil.parse(requestAttributes.getRequest().getHeader(CommonConstants.USER_AGENT));
        entity.setOs(userAgent.getOs().getName());
        entity.setBrowser(userAgent.getBrowser().getName());
        // 结果
        if (Objects.nonNull(exception)) {
            entity.setStatus(CommonConstants.FALSE);
            entity.setMessage(exception.getMessage());
        }
        if (returnVal instanceof Result<?>) {
            Result<?> result = (Result<?>) returnVal;
            entity.setStatus(result.getCode() == 200 ? CommonConstants.TRUE : CommonConstants.FALSE);
            entity.setMessage(result.getMessage());
        }
        entity.setResult(JSON.toJSONString(returnVal));
        operationLogMapper.insert(entity);
    }

    /**
     * 记录 TODO 登录日志
     * @param joinPoint 切点
     * @param returnVal 返回值
     * @param exception 异常
     */
    @Async
    public void recordLoginLog(JoinPoint joinPoint, Object returnVal, Exception exception) {

    }
}
