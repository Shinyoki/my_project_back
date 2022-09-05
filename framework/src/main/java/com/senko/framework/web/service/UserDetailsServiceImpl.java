package com.senko.framework.web.service;






import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.senko.common.core.entity.SysUser;
import com.senko.common.core.entity.SysUserInfo;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.system.mapper.ISysRoleMapper;
import com.senko.system.mapper.ISysUserInfoMapper;
import com.senko.system.mapper.ISysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户细节服务
 *
 * 负责查询出用户，并将用户信息封装成UserDetails对象
 * @author senko
 * @date 2022/8/26 22:37
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserMapper userMapper;

    @Autowired
    private ISysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 校验用户名不为空
        if (StringUtils.isBlank(username)) {
            throw new ServiceException("登录失败，请输入有效的用户名");
        }
        // 加载用户
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
        // 判断用户是否存在
        if (Objects.isNull(sysUser)) {
            throw new ServiceException("该账号还未注册！");
        }
        // 封装用户信息
        return buildUserDetails(sysUser);
    }

    public LoginUser buildUserDetails(SysUser user) {
        return new LoginUser(user, getUserRoles(user.getId()));
    }

    public Set<? extends GrantedAuthority> getUserRoles(Long userId) {
        return roleMapper.listUserRoles(userId).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
