package com.senko.common.exceptions.user;

/**
 * 不会打印printStackTrace的异常
 *
 * @author senko
 * @date 2022/9/12 11:21
 */
public class IgnoredUserException extends RuntimeException{

    public IgnoredUserException(String message) {
        super(message);
    }

    public IgnoredUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
