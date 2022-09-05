package com.senko.framework.config.security.manager;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 访问资源 拒绝与否 处理器
 *
 * URL 资源所需权限在这里获取
 * @author senko
 * @date 2022/9/5 12:41
 */
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    /**
     *
     * @param authentication        当前线程存储的用户
     * @param object                如果是URL资源，比如 /getUser，则是 FilterInvocation
     * @param configAttributes      所需的角色
     *
     * @throws AccessDeniedException                    没权限时抛出的异常
     * @throws InsufficientAuthenticationException      没登录时抛出的异常
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        // 用户的权限
        Set<String> userAuthorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // 筛选，有则return放行，没有则抛出AccessDeniedException异常
        for (ConfigAttribute configAttribute : configAttributes) {
            if (userAuthorities.contains(configAttribute.getAttribute())) {
                return;
            }
        }

        // 没有权限
        throw new AccessDeniedException("用户没有权限访问该资源");

    }

    /**
     * 基本都是 URL 资源，就都 能处理了
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
