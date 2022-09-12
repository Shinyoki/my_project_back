package com.senko.common.exceptions.user;

/**
 * 角色被禁用异常
 *
 * @author senko
 * @date 2022/9/12 11:20
 */
public class UserRoleDisabledException extends IgnoredUserException{
    public UserRoleDisabledException(String message) {
        super(message);
    }

    public UserRoleDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}
