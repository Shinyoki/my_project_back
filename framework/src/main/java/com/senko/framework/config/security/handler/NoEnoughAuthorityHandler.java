package com.senko.framework.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.senko.common.constants.HttpStatus;
import com.senko.common.core.entity.Result;
import com.senko.common.utils.http.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * 认证失败 处理
 *
 * @author senko
 * @date 2022/9/5 9:19
 */
@Component
public class NoEnoughAuthorityHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String message = MessageFormat.format("请求访问：{0}失败，请尝试登录后访问！", request.getRequestURI());
        ServletUtils.renderJSONResult(
                JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED, message))
        );

    }

}
