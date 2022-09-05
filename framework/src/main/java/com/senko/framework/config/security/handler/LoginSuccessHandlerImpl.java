package com.senko.framework.config.security.handler;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功Handler
 *
 * @author senko
 * @date 2022/9/3 20:56
 */
@Component
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.info("登录成功后置处理：{}", authentication.getName());
//        ServletUtils.renderJSONResult(JSON.toJSONString(Result.ok("登录成功")));

    }

}
