package com.senko.framework.aspetc;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.senko.common.annotations.LogOperation;
import com.senko.common.constants.CommonConstants;
import com.senko.common.core.dto.LoginUserDTO;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysLoginLog;
import com.senko.common.core.entity.SysOperationLog;
import com.senko.common.utils.async.AsyncManager;
import com.senko.common.utils.http.ServletUtils;
import com.senko.common.utils.ip.IpUtils;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.web.core.service.AsyncLogService;
import com.senko.framework.web.service.LoginUser;
import com.senko.system.mapper.ISysLoginLogMapper;
import com.senko.system.mapper.ISysOperationLogMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志切面注解
 *
 * @author senko
 * @date 2022/8/26 20:12
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ISysLoginLogMapper loginLogMapper;

    /**
     * 操作处理成功切面
     *
     * @param joinPoint 切入点
     * @param logEnum   切入点注解
     * @param returnVal 切入点返回值
     */
    @AfterReturning(pointcut = "@annotation(logEnum)", returning = "returnVal")
    public void handleOperationLogReturn(final JoinPoint joinPoint, LogOperation logEnum, Object returnVal) {
        recordOperationLog(joinPoint, logEnum, returnVal, null);
    }

    /**
     * 操作处理失败注解
     *
     * @param joinPoint 切入点
     * @param logEnum   切入点注解
     * @param e         抛出的异常
     */
    @AfterThrowing(pointcut = "@annotation(logEnum)", throwing = "e")
    public void handleOperationLogThrows(JoinPoint joinPoint, LogOperation logEnum, Exception e) {
        recordOperationLog(joinPoint, logEnum, null, e);
    }

    public void recordOperationLog(JoinPoint joinPoint, LogOperation logEnum, Object returnVal, Exception exception) {
        // 获取请求信息
        HttpServletRequest request = ServletUtils.getRequest();
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
        entity.setIp(IpUtils.getIpAddr(request));
        // 接口相关
        entity.setMethod(request.getMethod());
        // 拼接方法的类路径和方法名
        entity.setHandler(topClass.getName() + "#" + method.getName());
        entity.setUrl(request.getRequestURI());
        entity.setModule(api.tags()[0]);
        entity.setType(logOperation.value().getMethod());
        entity.setDes(annotation.value());
        entity.setParams(request.getQueryString());
        // 操作环境
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader(CommonConstants.USER_AGENT));
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

        AsyncManager.getInstance()
                .execute(AsyncLogService.getOperationLogTask(entity));
    }

    /**
     * 登录成功切面
     */
    @AfterReturning(pointcut = "execution(* com.senko.framework.web.core.service.ISysUserService.doLogin(..))", returning = "returnVal")
    public void handleLoginLogReturn(final JoinPoint joinPoint, Object returnVal) {
        recordLoginLog(joinPoint, returnVal, null);
    }

    /**
     * 登录失败切面
     */
    @AfterThrowing(pointcut = "execution(* com.senko.framework.web.core.service.ISysUserService.doLogin(..))", throwing = "e")
    public void handleLoginLogThrows(JoinPoint joinPoint, Exception e) {
        recordLoginLog(joinPoint, null, e);
    }

    public void recordLoginLog(JoinPoint joinPoint, Object returnVal, Exception exception) {

        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader(CommonConstants.USER_AGENT));

        SysLoginLog entity = new SysLoginLog();
        // 请求信息
        entity.setBrowser(userAgent.getBrowser().getName());
        entity.setOs(userAgent.getOs().getName());
        entity.setIp(IpUtils.getIpAddr(request));
        // 输入信息
        entity.setUsername((String) args[0]);
        entity.setPassword((String) args[1]);
        // 执行状态
        if (Objects.nonNull(returnVal) && returnVal instanceof LoginUserDTO) {
            entity.setStatus(CommonConstants.TRUE);
            entity.setMessage("登录成功");
        }
        if (Objects.nonNull(exception)) {
            entity.setStatus(CommonConstants.FALSE);
            entity.setMessage(exception.getMessage());
        }

        AsyncManager.getInstance()
                .execute(AsyncLogService.getLoginLogTask(entity));

    }

}
