package com.senko.controller.system;

import com.senko.common.constants.HttpStatus;
import com.senko.common.core.entity.Result;
import com.senko.common.core.vo.UserLoginVO;
import com.senko.common.core.vo.UserRegisterVO;
import com.senko.framework.web.core.service.ISysUserService;
import com.senko.framework.web.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 系统用户Controller
 *
 * @author senko
 * @date 2022/9/3 17:08
 */
@RestController
@Api(tags = "系统用户模块")
public class SysUserController {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("验证Token有效性")
    @PostMapping("/validToken")
    public Result<?> validateToken(String token) {
        logger.info("需要被验证的Token为：{}", token);
        boolean flag = tokenService.validateToken(token);
        if (flag) {
            return Result.ok("Token有效");
        } else {
            return Result.error(4001, "Token无效");
        }
    }

    /**
     * 注册
     * @param userRegisterVO    用户名、密码
     * @return                  注册结果
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<?> doRegister(@Valid UserRegisterVO userRegisterVO) {
        sysUserService.registerUser(userRegisterVO.getUsername(), userRegisterVO.getPassword());
        logger.info("用户注册成功：{}", userRegisterVO.getUsername());
        return Result.ok("用户注册成功！");
    }

    /**
     * 用户登录
     * @param loginVO   用户名、密码
     */
    @PostMapping(value = "/login")
    @ApiOperation("用户登录")
    public Result<?> login(@Valid @RequestBody UserLoginVO loginVO) {

        // 处理登录、并提供TOKEN
        String token = sysUserService.doLogin(loginVO.getUsername(), loginVO.getPassword());
        logger.info("用户登录成功：{}，Jwt: {}", loginVO.getUsername(), token);
        return Result.ok("登录成功！", token);

    }

}
