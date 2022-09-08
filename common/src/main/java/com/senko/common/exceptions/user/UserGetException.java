package com.senko.common.exceptions.user;

/**
 * 获取用户失败异常
 *
 * @author senko
 * @date 2022/9/7 22:51
 */
public class UserGetException extends RuntimeException{

    public UserGetException(String message) {
        super(message);
    }

    public UserGetException(String message, Throwable cause) {
        super(message, cause);
    }
}
