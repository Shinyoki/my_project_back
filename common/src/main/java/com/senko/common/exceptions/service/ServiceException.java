package com.senko.common.exceptions.service;

/**
 * 业务异常
 *
 * @author senko
 * @date 2022/8/26 20:22
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

}
