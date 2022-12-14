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
 * Web????????????
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
     * ????????????Authentication Bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * ????????????
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // ?????????????????????????????????
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
                "/doc.html",                    // ?????? swagger ??????
                "/webjars/**",                  // ?????? swagger ??????
                "/swagger-resources/**",        // ?????? swagger ??????
                "/v2/api-docs/**",              // ?????? swagger ??????
                "/captcha",      // ???????????????
                "/ws/**"
        );
    }

    /**
     * ??????
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try {

            // csrf????????????
            http.csrf()
                    .disable();

            // ??????token??????????????????session
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // ???????????????url
            http.authorizeRequests()
                    // ???????????? ?????????????????????????????? accessDecision ??? metadataSource???
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O interceptor) {
                            // ?????????????????????
                            interceptor.setAccessDecisionManager(accessDecisionManager);
                            // ???????????????????????????
                            interceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                            return interceptor;
                        }
                    })
                    // ????????????
                    .antMatchers("/test/**").permitAll()
                    .anyRequest().authenticated();

            // ?????????
            http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

            // ??????
            http.formLogin()
                    //???????????? /login
                    .loginProcessingUrl("/login")
                    //??????????????????
                    .successHandler(authenticationSuccessHandler)
                    //??????????????????
                    .failureHandler(authenticationFailureHandler);

            // ??????
            http.logout()
                    // ???????????????url
                    .logoutUrl("/logout")
                    // ?????????????????????
                    .logoutSuccessHandler(logoutSuccessHandler);

            // ????????????????????????
            http.exceptionHandling()
                    // ?????????????????????
                    .accessDeniedHandler(accessDeniedHandler)
                    // ?????????
                    .authenticationEntryPoint(authenticationEntryPoint);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
