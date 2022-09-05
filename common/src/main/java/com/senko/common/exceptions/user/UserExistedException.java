package com.senko.common.exceptions.user;

/**
 * 用户已存在异常
 *
 * @author senko
 * @date 2022/9/4 20:57
 */
public class UserExistedException extends RuntimeException{
    public UserExistedException(String s) {
        super(s);
    }
}
