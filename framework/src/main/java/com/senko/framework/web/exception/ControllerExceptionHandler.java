package com.senko.framework.web.exception;

import com.senko.common.core.entity.Result;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.exceptions.user.UserExistedException;
import com.senko.common.exceptions.user.UserPasswordRetryLimitException;
import com.senko.common.exceptions.user.UsernamePasswordException;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * MVC层的异常兜底处理，
 * 只能处理发生在Controller、Service里的异常
 *
 * @author senko
 * @date 2022/9/3 18:51
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler(UserPasswordRetryLimitException.class)
    public Result<?> handleUserPasswordRetryLimitException(UserPasswordRetryLimitException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler(UserExistedException.class)
    public Result<?> handleUserExistedException(UserExistedException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler({UsernamePasswordException.class})
    public Result<?> usernamePasswordException(UsernamePasswordException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceException(ServiceException e, HttpServletRequest request) {
        return handlerException(e, request, "操作失败！", true);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> basicError(Exception e, HttpServletRequest request) {
        return handlerException(e, request, "服务器内部异常！", true);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return handlerException(e, request, "请求方式不支持！", false);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<?> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        return handlerException(e, request, "请求媒体类型不支持！" + e.getMessage(), false);
    }

    /**
     * 异常处理
     * @param e                     异常
     * @param request               当前请求
     * @param message               友好信息反馈
     * @param printStackTrace       是否打印堆栈信息
     */
    public Result<?> handlerException(Exception e, HttpServletRequest request, String message, Boolean printStackTrace) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        logger.error("请求地址：\"{}\"，请求方式：\"{}\"\n，发生异常:\"{}\"", requestURI, method, e.getMessage());
        if (printStackTrace) {
            e.printStackTrace();
        }

        return Result.error(message);
    }

}
