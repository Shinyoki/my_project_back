package com.senko.framework.web.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.senko.common.core.dto.LoginUserDTO;
import com.senko.common.core.dto.OnlineUserDTO;
import com.senko.common.core.dto.SysUserDTO;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.SysUser;
import com.senko.common.core.entity.SysUserVO;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.common.core.vo.SysBackUserVO;

import java.util.List;
import java.util.Set;

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

    /**
     * 获取后台用户集合
     * @param sysUserVO   分页参数、查询条件
     */
    PageResult<SysUserDTO> listBackUsers(SysUserVO sysUserVO);

    /**
     * 添加或删除用户
     */
    void saveOrUpdateSysUser(SysBackUserVO sysBackUserVO);

    /**
     * 获取在线用户集合
     * @param username  用户名
     */
    List<OnlineUserDTO> listOnlineUsers(String username);

    /**
     * 强制下线
     * @param sessionUIDList    sessionUID集合
     */
    void kickOutOnlineUsers(Set<String> sessionUIDList);

}
