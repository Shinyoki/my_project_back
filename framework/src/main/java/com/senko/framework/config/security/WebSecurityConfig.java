package com.senko.framework.config.security;

import com.senko.framework.config.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Web认证配置
 *
 * @author senko
 * @date 2022/8/26 22:12
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AccessDecisionManager accessDecisionManager;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 重复注册Authentication Bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用户认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // 放行路径（不走拦截链）
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/websocket/**",
                "/**.html",
                "/login/**",
                "/register/**",
                "/css/**",
                "/js/**",
                "/img/**",
                "/fonts/**",
                "favicon.ico",
                "/doc.html",                    // 放行 swagger 资源
                "/webjars/**",                  // 放行 swagger 资源
                "/swagger-resources/**",        // 放行 swagger 资源
                "/v2/api-docs/**",              // 放行 swagger 资源
                "/captcha",      // 验证码接口
                "/ws/**"
        );
    }

    /**
     * 权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try {

            // csrf防护关闭
            http.csrf()
                    .disable();

            // 基于token，所以不需要session
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // 放开权限的url
            http.authorizeRequests()
                    // 动态权限 （后处理给过滤器设置 accessDecision 和 metadataSource）
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O interceptor) {
                            // 设置决策管理器
                            interceptor.setAccessDecisionManager(accessDecisionManager);
                            // 设置资源源数据定义
                            interceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                            return interceptor;
                        }
                    })
                    // 放开测试
                    .antMatchers("/test/**").permitAll()
                    .anyRequest().authenticated();

            // 过滤器
            http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

            // 登录
            http.formLogin()
                    //默认就是 /login
                    .loginProcessingUrl("/login")
                    //登录成功受理
                    .successHandler(authenticationSuccessHandler)
                    //登录失败受理
                    .failureHandler(authenticationFailureHandler);

            // 注销
            http.logout()
                    // 负责注销的url
                    .logoutUrl("/logout")
                    // 注销的后置处理
                    .logoutSuccessHandler(logoutSuccessHandler);

            // 未登录、权限不足
            http.exceptionHandling()
                    // 登录但权限不足
                    .accessDeniedHandler(accessDeniedHandler)
                    // 未登录
                    .authenticationEntryPoint(authenticationEntryPoint);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
