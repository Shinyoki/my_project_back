package com.senko.common.exceptions.service;

/**
 * 分页请求参数错误
 *
 * @author senko
 * @date 2022/9/10 16:16
 */
public class PageRequestException extends ServiceException{

    public PageRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageRequestException(String message) {
        super(message);
    }
}
