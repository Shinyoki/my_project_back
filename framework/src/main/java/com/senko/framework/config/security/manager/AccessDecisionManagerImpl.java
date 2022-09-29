package com.senko.framework.config.security.manager;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.web.core.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
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

    private List<String> disabledRoles;

    @Autowired
    private ISysRoleService roleService;

    @PostConstruct
    private void listDisabledRoles() {
        disabledRoles = roleService.listDisabledRoles();
    }

    public void clearDisabledRoles() {
        disabledRoles = null;
    }

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

        if (object instanceof FilterInvocation) {
            FilterInvocation filterInvocation = (FilterInvocation) object;
            if (CollectionUtils.isEmpty(disabledRoles)) {
                listDisabledRoles();
            }

            Optional.ofNullable(SecurityUtils.getLoginUserIfHasLogin()).ifPresent(loginUser -> {
                Integer isDisabled = loginUser.getIsDisabled();
                if (Objects.nonNull(isDisabled) && Objects.equals(isDisabled, 1)) {
                    throw new AccessDeniedException("用户角色已被禁用");
                }
            });

            // 用户的权限
            Set<String> userAuthorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            // 筛选，有则return放行，没有则抛出AccessDeniedException异常
            for (ConfigAttribute configAttribute : configAttributes) {
                String curNeededRole = configAttribute.getAttribute();
                if (userAuthorities.contains(curNeededRole)) {
                    // 用户有角色
                    if (disabledRoles.contains(curNeededRole)) {
                        // 但是该角色被禁用了
                        throw new AccessDeniedException("该用户的所在角色已被禁用，无法访问资源" + filterInvocation.getRequestUrl() + "，请尝试切换账号或联系管理员");
                    }
                    // 有权限，放行
                    return;
                }
            }

            // 没有权限
            throw new AccessDeniedException("用户没有权限访问该资源：" + filterInvocation.getRequestUrl());
        }

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
