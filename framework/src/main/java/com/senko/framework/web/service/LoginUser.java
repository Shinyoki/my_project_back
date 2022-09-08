package com.senko.framework.web.service;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.senko.common.core.entity.SysUser;
import com.senko.common.core.entity.SysUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录用户 实体
 *
 * @author senko
 * @date 2022/8/26 22:39
 */
@Data
@AllArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户信息ID
     */
    private Long userInfoId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录IP
     */
    private String ip;

    // ===============jwt=================
    /**
     * uuid
     */
    private String uuid;

    /**
     * 登录时间
     */
    private Long lastLoginTime;

    /**
     * token 过期时间
     */
    private Long expireTime;


    // ==============Security=============
    /**
     * 权限
     */
    private Set<? extends GrantedAuthority> authorities;

    /**
     *  获取角色
     */
    public Set<String> getRoles() {
        if (CollectionUtils.isNotEmpty(authorities)) {
            Set<String> roles = new HashSet<>(authorities.size());
            authorities.forEach(authority -> roles.add(authority.getAuthority()));
            return roles;
        }
        return null;
    }

    public void setUserInfo(SysUserInfo userInfo) {
        this.browser = userInfo.getBrowser();
        this.os = userInfo.getOs();
        this.ip = userInfo.getIp();
        this.nickname = userInfo.getNickname();
        this.avatar = userInfo.getAvatar();
    }

    public void setSysUser(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.userInfoId = user.getUserInfoId();
    }

    public LoginUser(SysUser user, SysUserInfo userInfo, Set<? extends GrantedAuthority> authorities) {
        setSysUser(user);
        setUserInfo(userInfo);
        this.authorities = authorities;
    }

    public LoginUser(SysUser user, Set<? extends GrantedAuthority> authorities) {
        setSysUser(user);
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return true;
    }
}
