package com.senko.common.constants;

/**
 * Redis缓存
 *
 * PREFIX/TAG常量
 * @author senko
 * @date 2022/9/4 15:30
 */
public class RedisConstants {

    /**
     * 用户登录失败次数 PREFIX
     * key: 'user_pwd_err_cnt:'
     * val: count: Integer
     */
    public static final String USER_PWD_ERR_cnt_PREFIX = "user_pwd_err_cnt:";

    /**
     * 用户登录 PREFIX
     *
     * key: 'user_login_token:'
     * val: token: String
     */
    public static final String USER_LOGIN_TOKEN_PREFIX = "user_login_token:";

    /**
     * 用户登录 TAG
     */
    public static final String USER_TOKEN_TAG = "user_login_token";

    /**
     * 限流 Prefix
     */
    public static final String ACCESS_LIMIT_PREFIX = "access:";

    /**
     * 重复提交 Prefix
     */
    public static final String REPEAT_SUBMIT_PREFIX = "repeat:";

}
