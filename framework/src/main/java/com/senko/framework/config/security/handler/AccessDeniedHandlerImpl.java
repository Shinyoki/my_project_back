package com.senko.framework.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.senko.common.constants.HttpStatus;
import com.senko.common.core.entity.Result;
import com.senko.common.utils.http.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * 登录了但权限不足异常处理
 *
 * @author senko
 * @date 2022/9/5 19:47
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ServletUtils.renderJSONResult(JSON.toJSONString(Result.error(HttpStatus.FORBIDDEN, MessageFormat.format("请求访问：{0} 失败，权限不足！", request.getRequestURI()))));

    }

}
