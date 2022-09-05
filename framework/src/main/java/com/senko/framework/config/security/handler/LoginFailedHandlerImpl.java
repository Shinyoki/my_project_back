package com.senko.framework.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.senko.common.constants.HttpStatus;
import com.senko.common.core.entity.Result;
import com.senko.common.utils.http.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 *
 * @author senko
 * @date 2022/9/3 20:57
 */
@Component
public class LoginFailedHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String result = JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED, "登录失败"));
        ServletUtils.renderJSONResult(result);

    }

}
