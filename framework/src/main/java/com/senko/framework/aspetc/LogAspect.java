package com.senko.framework.aspetc;

import com.senko.common.annotations.LogOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

/**
 * 日志切面注解
 *
 * @author senko
 * @date 2022/8/26 20:12
 */
@Aspect
public class LogAspect {

    /**
     * 处理成功切面
     * @param joinPoint     切入点
     * @param logEnum       切入点注解
     * @param returnVal     切入点返回值
     */
    @AfterReturning(pointcut = "@annotation(logEnum)", returning = "returnVal")
    public void handleReturn(final JoinPoint joinPoint, LogOperation logEnum, Object returnVal) {
        handleAll(joinPoint, logEnum, returnVal, null);
    }

    /**
     * 处理失败注解
     * @param joinPoint     切入点
     * @param logEnum       切入点注解
     * @param e             抛出的异常
     */
    @AfterThrowing(pointcut = "@annotation(logEnum)", throwing = "e")
    public void handleException(JoinPoint joinPoint, LogOperation logEnum, Exception e) {
        handleAll(joinPoint, logEnum, null, e);
    }

    protected void handleAll(JoinPoint joinPoint, LogOperation logEnum, Object returnVal, Exception exception) {
        // TODO 日志切面处理
    }

}
