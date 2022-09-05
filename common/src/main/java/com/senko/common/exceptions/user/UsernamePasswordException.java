package com.senko.common.exceptions.user;

/**
 * 用户名密码错误异常
 *
 * @author senko
 * @date 2022/9/4 15:24
 */
public class UsernamePasswordException extends RuntimeException{

    public UsernamePasswordException(String message) {
        super(message);
    }

}
