package com.senko.common.exceptions.user;

/**
 * 基础用户服务异常
 *
 * @author senko
 * @date 2022/9/4 15:40
 */
public class UserPasswordRetryLimitException extends RuntimeException{
    public UserPasswordRetryLimitException(String message) {
        super(message);
    }

    public UserPasswordRetryLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
