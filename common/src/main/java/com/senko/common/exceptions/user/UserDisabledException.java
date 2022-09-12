package com.senko.common.exceptions.user;

/**
 * 用户禁用异常
 *
 * @author senko
 * @date 2022/9/12 11:40
 */
public class UserDisabledException extends IgnoredUserException{

    public UserDisabledException(String message) {
        super(message);
    }
}
