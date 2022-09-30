package com.senko.framework.web.exception;

import com.alibaba.fastjson.JSON;
import com.senko.common.constants.HttpStatus;
import com.senko.common.core.entity.Result;
import com.senko.common.exceptions.service.PageRequestException;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.exceptions.user.*;
import com.senko.common.utils.http.ServletUtils;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.error("参数校验异常：{}", e.getMessage());
        return Result.error(HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(UserRoleDisabledException.class)
    public Result<?> userRoleDisabledException(UserRoleDisabledException e, HttpServletRequest request) {
        return Result.error(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(PageRequestException.class)
    public Result<?> pageRequestException(PageRequestException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        // ignored
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

    @ExceptionHandler(UserGetException.class)
    public Result<?> userGetException(UserGetException e, HttpServletRequest request) {
        logger.error("请求地址：\"{}\"，请求方式：\"{}\"\n，发生异常:\"{}\"", request.getRequestURI(), request.getMethod(), e.getMessage());
        e.printStackTrace();
        return Result.error(HttpStatus.UNAUTHORIZED, "用户状态异常，无法执行请求！");
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

    @ExceptionHandler({IgnoredUserException.class})
    public Result<?> ignoredUserException(IgnoredUserException e, HttpServletRequest request) {
        return handlerException(e, request, e.getMessage(), false);
    }

    /**
     * 异常处理
     *
     * @param e               异常
     * @param request         当前请求
     * @param message         友好信息反馈
     * @param printStackTrace 是否打印堆栈信息
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
