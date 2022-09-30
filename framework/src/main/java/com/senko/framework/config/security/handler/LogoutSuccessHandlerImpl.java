package com.senko.framework.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysLoginLog;
import com.senko.common.utils.async.AsyncManager;
import com.senko.common.utils.http.ServletUtils;
import com.senko.framework.web.core.service.AsyncLogService;
import com.senko.framework.web.service.LoginUser;
import com.senko.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 注销成功Handler
 *
 * @author senko
 * @date 2022/9/3 21:03
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 删除token缓存
        LoginUser loginUser = tokenService.getUserForRequest(request);
        if (Objects.nonNull(loginUser)) {
            tokenService.deleteCacheUser(loginUser);
        }

        // 返回json
        ServletUtils.renderJSONResult(JSON.toJSONString(Result.ok("退出登录成功")));

    }

}
