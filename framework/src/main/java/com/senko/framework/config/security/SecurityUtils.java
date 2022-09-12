package com.senko.framework.config.security;


import com.senko.common.exceptions.user.UserGetException;
import com.senko.framework.web.service.LoginUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * Spring Security工具类
 *
 * @author senko
 * @date 2022/9/5 8:13
 */
public class SecurityUtils {

    /**
     * 获取SecurityContext
     */
    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    /**
     * 获取当前请求的Authentication实例
     */
    public static Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    public static LoginUser getLoginUserIfHasLogin() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }

    /**
     * 获取登录用户，如是匿名用户或其他则返回null
     */
    public static LoginUser getLoginUser() {

        Authentication authentication = getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 需要的就是用户，没有则抛出异常
            throw new UserGetException("获取用户失败，当前用户为匿名用户");
        }

        if (Objects.isNull(authentication)) {
            throw new UserGetException("当前上下文中并没有存储用户");
        }

        try {
            Object principal = authentication.getPrincipal();
            if (Objects.isNull(principal)) {
                throw new UserGetException("获取到的用户为空！");
            }
            // 已登录用户
            return (LoginUser) principal;
        } catch (ClassCastException e) {
            throw new UserGetException("转型为登录用户失败！", e);
        }

    }

    /**
     * 获取登录用户ID
     */
    public static Long getUserAuthId() {
        return getLoginUser().getId();
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户昵称
     */
    public static String getNickname() {
        return getLoginUser().getNickname();
    }

    /**
     * 设置登录用户
     */
    public static void setAuthentication(Authentication authentication) {
        getSecurityContext().setAuthentication(authentication);
    }

}
