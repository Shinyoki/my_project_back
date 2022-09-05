package com.senko.framework.config.security;


import com.senko.framework.web.service.LoginUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    /**
     * 获取登录用户，如是匿名用户或其他则返回null
     */
    public static LoginUser getLoginUser() {

        Authentication authentication = getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 匿名用户，返回null
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser) {
            // 已登录用户
            return (LoginUser) principal;
        }
        return null;

    }

    /**
     * 设置登录用户
     */
    public static void setAuthentication(Authentication authentication) {
        getSecurityContext().setAuthentication(authentication);
    }

}
