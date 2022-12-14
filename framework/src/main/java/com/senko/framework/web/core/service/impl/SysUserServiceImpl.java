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
 * ????????????Service
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
     * ????????????
     *
     * @param username ?????????
     * @param password ??????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerUser(String username, String password) {

        // ?????????????????????????????????????????????
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUsername)
                .eq(SysUser::getUsername, username));

        if (Objects.nonNull(user)) {
            // ??????????????????????????????????????????
            throw new UserExistedException("?????????????????????????????????");
        }

        // ????????????????????????????????????
        UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest()
                .getHeader("User-Agent"));

        // tb user info
        SysUserInfo userInfo = SysUserInfo.builder()
                .nickname("??????" + UUID.fastUUID().toString().substring(0, 11))
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

        logger.info("?????????????????????????????????{}????????????{}", username, password);

    }

    /**
     * ????????????
     *
     * @param username ?????????
     * @param password ??????
     * @return ??????????????????TOKEN
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginUserDTO doLogin(String username, String password) {

        // ??????????????????????????????????????????
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ServiceException("?????????????????????????????????");
        }

        // ??????????????????????????????
        String retryCacheKey = RedisConstants.USER_PWD_ERR_cnt_PREFIX + username;
        // ?????????????????????????????????????????????????????????????????????
        LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(username);

        // ????????????
        if (!passwordEncoder.matches(password, loginUser.getPassword())) {

            // ?????????????????????????????????
            Long retryCount = redisHandler.incrementAndExpire(retryCacheKey, senkoConfig.getRetryInterval());
            if (Objects.nonNull(retryCount) && retryCount > senkoConfig.getRetryLimit()) {
                throw new UserPasswordRetryLimitException("?????????????????????????????????????????????");
            }
            throw new UsernamePasswordException("???????????????????????????");

        }

        // ????????????????????????????????????????????????????????????????????????redis???????????????token??????
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ???????????????????????????????????????
        redisHandler.delete(retryCacheKey);

        // ??????????????????????????????????????????Authentication??????UserDetails????????????????????????????????????????????????
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        Long userInfoId = loginUser.getUserInfoId();

        // ??????????????????UserInfo
        SysUserInfo userInfo = SysUserInfo.builder()
                .id(userInfoId)
                .os(userAgent.getOs().getName())
                .browser(userAgent.getBrowser().getName())
                .lastLoginTime(LocalDateTime.now())
                .ip(IpUtils.getHostIp())
                .build();

        // ?????????????????????UserInfo
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
     * ????????????????????????
     *
     * @param sysUserVO ???????????????????????????
     */
    @Override
    public PageResult<SysUserDTO> listBackUsers(SysUserVO sysUserVO) {
        List<SysUserDTO> sysUserDTOList = userMapper.listBackUsers(PageUtils.getLimitFormatCurrent(),
                PageUtils.getSize(),
                sysUserVO);
        return new PageResult<>(sysUserDTOList.size(), sysUserDTOList);
    }

    /**
     * ?????????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateSysUser(SysBackUserVO sysBackUserVO) {
        // ??????tb_user_auth??????
        SysUser sysUser = SysUser.builder()
                .id(sysBackUserVO.getId())
                .isDisabled(sysBackUserVO.getIsDisabled())
                .password(StringUtils.isNotBlank(sysBackUserVO.getPassword()) ? passwordEncoder.encode(sysBackUserVO.getPassword()) : null)
                .email(sysBackUserVO.getEmail())
                .username(sysBackUserVO.getUsername())
                .build();

        this.saveOrUpdate(sysUser);
        // ??????????????????
        SysUser one = this.getOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserInfoId)
                .eq(SysUser::getId, sysUser.getId()));
        String nickname = StringUtils.isBlank(sysBackUserVO.getNickname()) ? "??????" + UUID.fastUUID().toString().substring(0, 11) : sysBackUserVO.getNickname();
        if (Objects.isNull(one.getUserInfoId())) {
            // ?????????????????????????????????ID?????????????????????????????????
            SysUserInfo userInfo = SysUserInfo.builder()
                    .nickname(nickname)
                    .build();
            userInfoService.save(userInfo);
            this.update(new LambdaUpdateWrapper<SysUser>()
                    .set(SysUser::getUserInfoId, userInfo.getId())
                    .eq(SysUser::getId, sysUser.getId()));
        } else {
            // ?????????????????????????????????ID??????????????????????????????
            SysUserInfo userInfo = SysUserInfo.builder()
                    .id(sysUser.getUserInfoId())
                    .nickname(nickname)
                    .build();
            userInfoService.updateById(userInfo);
        }

        SysUserRole sysUserRole = userRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, sysUser.getId()));

        // ?????????????????????????????????????????????
        if (Objects.nonNull(sysBackUserVO.getRoleId())) {
            // ?????????????????????????????????????????????
            userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, sysUser.getId()));
            sysUserRole = SysUserRole.builder()
                    .userId(sysUser.getId())
                    .roleId(sysBackUserVO.getRoleId())
                    .build();
            userRoleMapper.insert(sysUserRole);
        } else {
            // ?????????????????????
            if (Objects.isNull(sysUserRole)) {
                sysUserRole = SysUserRole.builder()
                        .userId(sysUser.getId())
                        // ????????????????????????ID???????????????????????????
                        .roleId(sysBackUserVO.getRoleId() == null ? CommonConstants.NORMAL_USER_ROLE_ID : sysBackUserVO.getRoleId())
                        .build();
                userRoleMapper.insert(sysUserRole);
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param username ?????????
     */
    @Override
    public List<OnlineUserDTO> listOnlineUsers(String username) {
        Collection<String> keys = redisHandler.keys(RedisConstants.USER_LOGIN_TOKEN_PREFIX + "*");
        List<OnlineUserDTO> onlineUserDTOList = new ArrayList<>();
        for (String redisKey : keys) {
            try {
                LoginUser loginUser = (LoginUser) redisHandler.get(redisKey);
                // ????????? ???????????????
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
     * ????????????
     *
     * @param sessionUIDList sessionUID??????
     */
    @Override
    public void kickOutOnlineUsers(Set<String> sessionUIDList) {
        if (CollectionUtils.isNotEmpty(sessionUIDList)) {
            Set<String> keys = sessionUIDList.stream()
                    .map(uid -> RedisConstants.USER_LOGIN_TOKEN_PREFIX + uid)
                    .collect(Collectors.toSet());
            Long count = redisHandler.deleteBath(keys);
            ServletUtils.renderJSONResult("???????????? " + count + " ?????????");
        }
    }

    /**
     * ??????????????????
     *
     * @param userIds ??????ID??????
     */
    @Override
    public void deleteUsers(Set<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            if (userIds.contains(CommonConstants.ADMIN_USER_ID)) {
                throw new ServiceException("??????????????????????????????");
            }
//            Set<Long> userInfoIds = userMapper.selectList(new LambdaQueryWrapper<SysUser>()
//                            .select(SysUser::getUserInfoId)
//                            .in(SysUser::getId, userIds))
//                    .stream().map(SysUser::getUserInfoId).collect(Collectors.toSet());
//            // ????????????
//            this.removeByIds(userIds);
//            // ????????????????????????
//            userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
//                    .in(SysUserRole::getUserId, userIds));
//            // ??????????????????
//            userInfoMapper.delete(new LambdaQueryWrapper<SysUserInfo>()
//                    .in(SysUserInfo::getId, userInfoIds));
            // ???????????????????????????????????????
            this.update(new LambdaUpdateWrapper<SysUser>()
                    .set(SysUser::getIsDelete, CommonConstants.TRUE)
                    .in(SysUser::getId, userIds));
        }
    }

    /**
     * ????????????????????????
     *
     * @param userInfoVO ????????????
     */
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        Long id = SecurityUtils.getLoginUser().getId();
        SysUser entity = this.getOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserInfoId)
                .eq(SysUser::getId, id));
        Long userInfoId = entity.getUserInfoId();

        // ????????????
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

        // ??????????????????
        userInfoMapper.updateById(userInfo);
    }

    /**
     * ????????????
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
     * ??????????????????
     * @param updatePasswordVO  ??????
     */
    @Override
    public void updatePassword(UpdatePasswordVO updatePasswordVO) {
        Long userId = SecurityUtils.getLoginUser().getId();
        SysUser entity = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getId, SysUser::getPassword)
                .eq(SysUser::getId, userId));
        if (!passwordEncoder.matches(updatePasswordVO.getOldPassword(), entity.getPassword())) {
            // ???????????????
            throw new ServiceException("???????????????");
        }
        if (passwordEncoder.matches(updatePasswordVO.getNewPassword(), entity.getPassword())) {
            // ?????????????????????????????????
            throw new ServiceException("?????????????????????????????????");
        }
        if (!StringUtils.equals(updatePasswordVO.getNewPassword(), updatePasswordVO.getConfirmPassword())) {
            // ?????????????????????????????????
            throw new ServiceException("?????????????????????????????????");
        }

        entity.setPassword(passwordEncoder.encode(updatePasswordVO.getNewPassword()));
        userMapper.updateById(entity);
    }

}
