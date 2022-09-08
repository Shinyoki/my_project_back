package com.senko.framework.web.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.LoginUserDTO;
import com.senko.common.core.entity.SysUser;

/**
 * 用户Service
 *
 * @author senko
 * @date 2022/8/31 13:47
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 注册用户
     * @param username  用户名
     * @param password  密码
     */
    void registerUser(String username, String password);

    /**
     * 用户登录
     * @param username  用户名
     * @param password  密码
     * @return          封装过的用户TOKEN
     */
    LoginUserDTO doLogin(String username, String password);

}
