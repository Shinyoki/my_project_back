package com.senko.controller.system;

import com.senko.common.core.dto.LoginUserDTO;
import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.dto.SysUserDTO;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysUserVO;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.common.core.vo.SysBackUserVO;
import com.senko.common.core.vo.UserLoginVO;
import com.senko.common.core.vo.UserRegisterVO;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.web.core.service.ISysMenuService;
import com.senko.framework.web.core.service.ISysUserService;
import com.senko.framework.web.service.LoginUser;
import com.senko.framework.web.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


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

    @Autowired
    private ISysMenuService menuService;


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
    public Result<LoginUserDTO> login(@Valid @RequestBody UserLoginVO loginVO) {

        // 处理登录、并提供TOKEN
        LoginUserDTO loginUserDTO = sysUserService.doLogin(loginVO.getUsername(), loginVO.getPassword());
        return Result.ok("登录成功！", loginUserDTO);

    }

    /**
     * 查询当前用户所能访问的菜单
     */
    @ApiOperation("获取当前用户所能访问的菜单")
    @GetMapping("/admin/user/menus")
    public Result<List<SysMenusDTO>> listMenusForCurUser() {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<SysMenusDTO> result = menuService.listMenusForUser(loginUser.getId());
        return Result.ok("查询菜单成功！", result);

    }

    /**
     * 获取后台用户集合
     * @param sysUserVO   分页参数、查询条件
     */
    @ApiOperation("获取后台用户集合")
    @GetMapping("/admin/users")
    public Result<PageResult<SysUserDTO>> listBackUsers(SysUserVO sysUserVO) {

        PageResult<SysUserDTO> result = sysUserService.listBackUsers(sysUserVO);
        return Result.ok("查询用户成功！", result);

    }

    /**
     * 添加或删除用户
     */
    @ApiOperation("添加或删除用户")
    @PostMapping("/admin/user")
    public Result<?> saveOrUpdateUser(@RequestBody SysBackUserVO sysBackUserVO) {
        sysUserService.saveOrUpdateSysUser(sysBackUserVO);
        return Result.ok("添加或删除用户成功！");
    }

}
