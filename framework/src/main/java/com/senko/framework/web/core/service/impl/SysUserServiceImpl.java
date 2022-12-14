package com.senko.framework.web.core.service.impl;


import cn.hutool.core.lang.UUID;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.senko.common.constants.CommonConstants;
import com.senko.common.constants.RedisConstants;
import com.senko.common.core.dto.LoginUserDTO;
import com.senko.common.core.dto.OnlineUserDTO;
import com.senko.common.core.dto.SysUserDTO;
import com.senko.common.core.entity.*;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.common.core.vo.SysBackUserVO;
import com.senko.common.core.vo.UpdatePasswordVO;
import com.senko.common.core.vo.UserInfoVO;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.exceptions.user.UserDisabledException;
import com.senko.common.exceptions.user.UserExistedException;
import com.senko.common.exceptions.user.UserPasswordRetryLimitException;
import com.senko.common.exceptions.user.UsernamePasswordException;
import com.senko.common.utils.http.ServletUtils;
import com.senko.common.utils.ip.IpUtils;
import com.senko.common.utils.page.PageUtils;
import com.senko.framework.config.SenkoConfig;
import com.senko.framework.config.redis.RedisHandler;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.web.core.service.ISysUserInfoService;
import com.senko.framework.web.service.LoginUser;
import com.senko.framework.web.service.TokenService;
import com.senko.system.mapper.ISysUserInfoMapper;
import com.senko.system.mapper.ISysUserMapper;
import com.senko.framework.web.core.service.ISysUserService;
import com.senko.system.mapper.ISysUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.awt.font.TextHitInfo;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户Service
 *
 * @author senko
 * @date 2022/8/31 13:48
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<ISysUserMapper, SysUser> implements ISysUserService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SenkoConfig senkoConfig;

    @Autowired
    private ISysUserMapper userMapper;

    @Autowired
    private ISysUserInfoMapper userInfoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysUserInfoService userInfoService;

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerUser(String username, String password) {

        // 查询数据库，检测用户名是否存在
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUsername)
                .eq(SysUser::getUsername, username));

        if (Objects.nonNull(user)) {
            // 已存在，则抛出异常，返回信息
            throw new UserExistedException("用户已存在，注册失败！");
        }

        // 不存在，则加密密码并存储
        UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest()
                .getHeader("User-Agent"));

        // tb user info
        SysUserInfo userInfo = SysUserInfo.builder()
                .nickname("用户" + UUID.fastUUID().toString().substring(0, 11))
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOs().getName())
                .lastLoginTime(LocalDateTime.now())
                .ip(IpUtils.getHostIp())
                .build();
        userInfoMapper.insert(userInfo);
        // tb user auth
        SysUser sysUser = SysUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .userInfoId(userInfo.getId())
                .build();
        userMapper.insert(sysUser);
        // tb user role
        SysUserRole userRole = SysUserRole.builder()
                .userId(sysUser.getId())
                .roleId(CommonConstants.NORMAL_USER_ROLE_ID)
                .build();
        userRoleMapper.insert(userRole);

        logger.info("用户注册成功，用户名：{}，密码：{}", username, password);

    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 封装过的用户TOKEN
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginUserDTO doLogin(String username, String password) {

        // 基本参数校验（虽然多此一举）
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ServiceException("用户名或密码不能为空！");
        }

        // 密码错误重试次数检查
        String retryCacheKey = RedisConstants.USER_PWD_ERR_cnt_PREFIX + username;
        // 加载用户，并检测是否存在，不存在则直接抛出异常
        LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(username);

        // 密码校验
        if (!passwordEncoder.matches(password, loginUser.getPassword())) {

            // 密码错误，增加校验次数
            Long retryCount = redisHandler.incrementAndExpire(retryCacheKey, senkoConfig.getRetryInterval());
            if (Objects.nonNull(retryCount) && retryCount > senkoConfig.getRetryLimit()) {
                throw new UserPasswordRetryLimitException("密码错误次数过多，请稍后再试！");
            }
            throw new UsernamePasswordException("用户名或密码错误！");

        }

        // 登录成功，则只需要清除密码错误次数、将对象缓存到redis并返回相应token即可
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 登录成功，删除密码错误次数
        redisHandler.delete(retryCacheKey);

        // 因为是修改对象，会同步影响给Authentication中的UserDetails，所以可以在这里直接修改用户信息
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        Long userInfoId = loginUser.getUserInfoId();

        // 修改对象中的UserInfo
        SysUserInfo userInfo = SysUserInfo.builder()
                .id(userInfoId)
                .os(userAgent.getOs().getName())
                .browser(userAgent.getBrowser().getName())
                .lastLoginTime(LocalDateTime.now())
                .ip(IpUtils.getHostIp())
                .build();

        // 修改数据库中的UserInfo
        userInfoMapper.updateById(userInfo);
        userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<SysUserInfo>()
                .eq(SysUserInfo::getId, userInfoId));

        loginUser.setUserInfo(userInfo);

        String token = tokenService.createToken(loginUser);

        return LoginUserDTO.builder()
                .token(token)
                .roles(loginUser.getRoles())
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .email(loginUser.getEmail())
                .build();
    }

    /**
     * 获取后台用户集合
     *
     * @param sysUserVO 分页参数、查询条件
     */
    @Override
    public PageResult<SysUserDTO> listBackUsers(SysUserVO sysUserVO) {
        List<SysUserDTO> sysUserDTOList = userMapper.listBackUsers(PageUtils.getLimitFormatCurrent(),
                PageUtils.getSize(),
                sysUserVO);
        return new PageResult<>(sysUserDTOList.size(), sysUserDTOList);
    }

    /**
     * 添加或删除用户
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateSysUser(SysBackUserVO sysBackUserVO) {
        // 封装tb_user_auth实体
        SysUser sysUser = SysUser.builder()
                .id(sysBackUserVO.getId())
                .isDisabled(sysBackUserVO.getIsDisabled())
                .password(StringUtils.isNotBlank(sysBackUserVO.getPassword()) ? passwordEncoder.encode(sysBackUserVO.getPassword()) : null)
                .email(sysBackUserVO.getEmail())
                .username(sysBackUserVO.getUsername())
                .build();

        this.saveOrUpdate(sysUser);
        // 获取用户实体
        SysUser one = this.getOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserInfoId)
                .eq(SysUser::getId, sysUser.getId()));
        String nickname = StringUtils.isBlank(sysBackUserVO.getNickname()) ? "用户" + UUID.fastUUID().toString().substring(0, 11) : sysBackUserVO.getNickname();
        if (Objects.isNull(one.getUserInfoId())) {
            // 如果用户实体的用户信息ID不存在，则新增用户信息
            SysUserInfo userInfo = SysUserInfo.builder()
                    .nickname(nickname)
                    .build();
            userInfoService.save(userInfo);
            this.update(new LambdaUpdateWrapper<SysUser>()
                    .set(SysUser::getUserInfoId, userInfo.getId())
                    .eq(SysUser::getId, sysUser.getId()));
        } else {
            // 如果用户实体的用户信息ID存在，则修改用户信息
            SysUserInfo userInfo = SysUserInfo.builder()
                    .id(sysUser.getUserInfoId())
                    .nickname(nickname)
                    .build();
            userInfoService.updateById(userInfo);
        }

        SysUserRole sysUserRole = userRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, sysUser.getId()));

        // 判断是否已经存在在用户角色关联
        if (Objects.nonNull(sysBackUserVO.getRoleId())) {
            // 已经存在，则删除并插入，以更新
            userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, sysUser.getId()));
            sysUserRole = SysUserRole.builder()
                    .userId(sysUser.getId())
                    .roleId(sysBackUserVO.getRoleId())
                    .build();
            userRoleMapper.insert(sysUserRole);
        } else {
            // 不存在，则添加
            if (Objects.isNull(sysUserRole)) {
                sysUserRole = SysUserRole.builder()
                        .userId(sysUser.getId())
                        // 如果没有传入角色ID，则默认为普通用户
                        .roleId(sysBackUserVO.getRoleId() == null ? CommonConstants.NORMAL_USER_ROLE_ID : sysBackUserVO.getRoleId())
                        .build();
                userRoleMapper.insert(sysUserRole);
            }
        }
    }

    /**
     * 获取在线用户集合
     *
     * @param username 用户名
     */
    @Override
    public List<OnlineUserDTO> listOnlineUsers(String username) {
        Collection<String> keys = redisHandler.keys(RedisConstants.USER_LOGIN_TOKEN_PREFIX + "*");
        List<OnlineUserDTO> onlineUserDTOList = new ArrayList<>();
        for (String redisKey : keys) {
            try {
                LoginUser loginUser = (LoginUser) redisHandler.get(redisKey);
                // 有约束 但是不符合
                if (StringUtils.isNotBlank(username) &&
                        !StringUtils.contains(loginUser.getUsername(), username)) {
                    continue;
                }
                OnlineUserDTO onlineUserDTO = OnlineUserDTO.builder()
                        .sessionUID(loginUser.getUuid())
                        .username(loginUser.getUsername())
                        .nickname(loginUser.getNickname())
                        .ip(loginUser.getIp())
                        .os(loginUser.getOs())
                        .browser(loginUser.getBrowser())
                        .loginTime(loginUser.getLastLoginTime())
                        .build();
                onlineUserDTOList.add(onlineUserDTO);

            } catch (ClassCastException ignored) {
            }
        }
        return onlineUserDTOList;
    }

    /**
     * 强制下线
     *
     * @param sessionUIDList sessionUID集合
     */
    @Override
    public void kickOutOnlineUsers(Set<String> sessionUIDList) {
        if (CollectionUtils.isNotEmpty(sessionUIDList)) {
            Set<String> keys = sessionUIDList.stream()
                    .map(uid -> RedisConstants.USER_LOGIN_TOKEN_PREFIX + uid)
                    .collect(Collectors.toSet());
            Long count = redisHandler.deleteBath(keys);
            ServletUtils.renderJSONResult("成功下线 " + count + " 个用户");
        }
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID集合
     */
    @Override
    public void deleteUsers(Set<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            if (userIds.contains(CommonConstants.ADMIN_USER_ID)) {
                throw new ServiceException("不允许删除超级管理员");
            }
//            Set<Long> userInfoIds = userMapper.selectList(new LambdaQueryWrapper<SysUser>()
//                            .select(SysUser::getUserInfoId)
//                            .in(SysUser::getId, userIds))
//                    .stream().map(SysUser::getUserInfoId).collect(Collectors.toSet());
//            // 删除用户
//            this.removeByIds(userIds);
//            // 删除用户角色映射
//            userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
//                    .in(SysUserRole::getUserId, userIds));
//            // 删除用户信息
//            userInfoMapper.delete(new LambdaQueryWrapper<SysUserInfo>()
//                    .in(SysUserInfo::getId, userInfoIds));
            // 不再物理删除，改用逻辑删除
            this.update(new LambdaUpdateWrapper<SysUser>()
                    .set(SysUser::getIsDelete, CommonConstants.TRUE)
                    .in(SysUser::getId, userIds));
        }
    }

    /**
     * 修改当前用户信息
     *
     * @param userInfoVO 用户信息
     */
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        Long id = SecurityUtils.getLoginUser().getId();
        SysUser entity = this.getOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserInfoId)
                .eq(SysUser::getId, id));
        Long userInfoId = entity.getUserInfoId();

        // 修改邮箱
        if (StringUtils.isNotBlank(userInfoVO.getEmail())) {
            SysUser sysUser = SysUser.builder()
                    .id(id)
                    .email(userInfoVO.getEmail())
                    .build();
            this.updateById(sysUser);
        }
        SysUserInfo userInfo = SysUserInfo.builder()
                .id(userInfoId)
                .avatar(userInfoVO.getAvatar())
                .nickname(userInfoVO.getNickname())
                .build();

        // 修改用户信息
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        LoginUser loginUserIfHasLogin = SecurityUtils.getLoginUserIfHasLogin();
        if (Objects.nonNull(loginUserIfHasLogin)) {
            String uuid = loginUserIfHasLogin.getUuid();
            tokenService.removeUserCache(uuid);
        }
    }

    /**
     * 更新用户密码
     * @param updatePasswordVO  密码
     */
    @Override
    public void updatePassword(UpdatePasswordVO updatePasswordVO) {
        Long userId = SecurityUtils.getLoginUser().getId();
        SysUser entity = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getId, SysUser::getPassword)
                .eq(SysUser::getId, userId));
        if (!passwordEncoder.matches(updatePasswordVO.getOldPassword(), entity.getPassword())) {
            // 旧密码错误
            throw new ServiceException("旧密码错误");
        }
        if (passwordEncoder.matches(updatePasswordVO.getNewPassword(), entity.getPassword())) {
            // 新密码不能与旧密码相同
            throw new ServiceException("新密码不能与旧密码相同");
        }
        if (!StringUtils.equals(updatePasswordVO.getNewPassword(), updatePasswordVO.getConfirmPassword())) {
            // 新密码与确认密码不一致
            throw new ServiceException("新密码与确认密码不一致");
        }

        entity.setPassword(passwordEncoder.encode(updatePasswordVO.getNewPassword()));
        userMapper.updateById(entity);
    }

}
