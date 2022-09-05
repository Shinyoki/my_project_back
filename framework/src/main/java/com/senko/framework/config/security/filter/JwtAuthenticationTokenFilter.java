package com.senko.framework.config.security.filter;

import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.web.service.LoginUser;
import com.senko.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Jwt 认证过滤器
 *
 * 在校验用户身份之前，先判断SecurityContext中是否设置了Token对应的用户
 * 如果Token有对应的缓存用户，而Context却没设置，则添加
 * 如两者都没有，进入UsernameAuthenticationFilter会校验失败
 *
 * 每次请求都会经过一次过滤
 * @author senko
 * @date 2022/9/5 8:03
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 通过token获取缓存用户
        LoginUser loginUser = tokenService.getUserForRequest(request);

        /**
         * 缓存中存在对象，但是Security上下文在没有存用户，说明刚刚登录成功，
         * 已经给前台返回了token并缓存了用户，
         * 于是在当前请求里把缓存对象存到Security上下文中
         */
        if (Objects.nonNull(loginUser) && Objects.isNull(SecurityUtils.getAuthentication())) {

            // 校验token是否有效
            tokenService.tryRefreshToken(loginUser);

            // 创建一个认证对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            // 向认证对象封装请求信息
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            // 将认证对象存到Security上下文中
            SecurityUtils.setAuthentication(authenticationToken);

        }
        // token失效或者未携带token，直接放行
        filterChain.doFilter(request, response);

    }

}
