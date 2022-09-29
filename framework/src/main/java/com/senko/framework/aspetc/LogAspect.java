package com.senko.framework.aspetc;

import com.senko.common.annotations.LogOperation;
import com.senko.framework.web.service.AsyncOperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private AsyncOperationLogService asyncOperationLogService;

    /**
     * 操作处理成功切面
     * @param joinPoint     切入点
     * @param logEnum       切入点注解
     * @param returnVal     切入点返回值
     */
    @AfterReturning(pointcut = "@annotation(logEnum)", returning = "returnVal")
    public void handleOperationLogReturn(final JoinPoint joinPoint, LogOperation logEnum, Object returnVal) {
        asyncOperationLogService.recordOperationLog(joinPoint, logEnum, returnVal, null);
    }

    /**
     * 操作处理失败注解
     * @param joinPoint     切入点
     * @param logEnum       切入点注解
     * @param e             抛出的异常
     */
    @AfterThrowing(pointcut = "@annotation(logEnum)", throwing = "e")
    public void handleOperationLogThrows(JoinPoint joinPoint, LogOperation logEnum, Exception e) {
        asyncOperationLogService.recordOperationLog(joinPoint, logEnum, null, e);
    }

    /**
     * 登录成功切面 com.senko.framework.web.core.service.ISysUserService#doLogin(java.lang.String, java.lang.String)
     */
    @AfterReturning(pointcut = "execution(* com.senko.framework.web.core.service.ISysUserService.doLogin(..))", returning = "returnVal")
    public void handleLoginLogReturn(final JoinPoint joinPoint, Object returnVal) {
        asyncOperationLogService.recordLoginLog(joinPoint, returnVal, null);
    }

    /**
     * 登录失败切面 com.senko.framework.web.core.service.ISysUserService#doLogin(java.lang.String, java.lang.String)
     */
    @AfterThrowing(pointcut = "execution(* com.senko.framework.web.core.service.ISysUserService.doLogin(..))", throwing = "e")
    public void handleLoginLogThrows(JoinPoint joinPoint, Exception e) {
        asyncOperationLogService.recordLoginLog(joinPoint, null, e);
    }
}
