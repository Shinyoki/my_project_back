package com.senko.framework.web.service;

import com.senko.common.constants.RedisConstants;
import com.senko.framework.config.redis.RedisHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * JWT 服务
 *
 * @author senko
 * @date 2022/9/4 18:50
 */
@Component
public class TokenService {

    /**
     * 令牌存储的请求头
     */
    @Value("${token.header}")
    private String requestHeaderName;

    /**
     * 令牌的前缀
     */
    @Value("${token.tokenPrefix}")
    private String tokenPrefix;

    /**
     * 加密令牌所用的秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * Token的有效时间 单位：分
     */
    @Value("${token.expireTime}")
    private Integer expireTime;

    @Autowired
    private RedisHandler redisHandler;

    private final long MILLIS_SECOND = 1000L;
    private final long MILLIS_MINUTE = 60 * 1000L;
    private final long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 获取缓存对应token的key
     *
     * @param uuid 用户唯一标识
     * @return 缓存的key
     */
    private String getUserCacheKey(String uuid) {
        return RedisConstants.USER_LOGIN_TOKEN_PREFIX + uuid;
    }

    /**
     * 通过请求中携带的token获取缓存中存储的用户
     *
     * @param request 请求
     * @return 用户
     */
    public LoginUser getUserForRequest(HttpServletRequest request) {

        // 获取请求携带的令牌
        String tokenFromRequest = getTokenFromRequest(request);
        if (StringUtils.isBlank(tokenFromRequest)) {
            return null;
        }

        // 提取存储的用户信息
        Claims claims = parseTokenStr(tokenFromRequest);
        String uuid = (String) claims.get(RedisConstants.USER_TOKEN_TAG);

        // 获取缓存中的用户信息
        return (LoginUser) redisHandler.get(getUserCacheKey(uuid));

    }

    public boolean validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        Claims claims = parseTokenStr(token);
        String uuid = (String) claims.get(RedisConstants.USER_TOKEN_TAG);
        String userCacheKey = getUserCacheKey(uuid);
        return redisHandler.containsKey(userCacheKey);
    }

    /**
     * 从Request请求中提取TOKEN
     *
     * @param request 请求
     * @return TOKEN
     */
    @Nullable
    public String getTokenFromRequest(HttpServletRequest request) {

        String result = null;
        String headerValue = request.getHeader(requestHeaderName);
        if (StringUtils.isNotBlank(headerValue) && headerValue.startsWith(tokenPrefix)) {
            // 提取到token，并加工为 token
            result = headerValue.substring(tokenPrefix.length());
        }
        return result;

    }

    /**
     * 封装用户特征为Token
     *
     * @param loginUser 用户信息
     * @return Token
     */
    public String createToken(LoginUser loginUser) {
        String uuid = UUID.randomUUID().toString();
        /**
         * 存储用户特征到 TOKEN
         */
        HashMap<String, Object> claims = new HashMap<String, Object>() {{
            // TOKEN 只存储用户特征
            put(RedisConstants.USER_TOKEN_TAG, uuid);
        }};

        /**
         * 修改用户的登录时间等信息
         */
        loginUser.setUuid(uuid);
        refreshToken(loginUser);

        return createToken(claims);
    }

    /**
     * 合适的时间下会刷新缓存
     *
     * @param loginUser 用户信息
     */
    public void tryRefreshToken(LoginUser loginUser) {
        Long expireMillis = loginUser.getExpireTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (MILLIS_MINUTE_TEN >= (expireMillis - currentTimeMillis)) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新缓存的过期时间
     *
     * @param loginUser 用户信息
     */
    public void refreshToken(LoginUser loginUser) {
        long curMillis = System.currentTimeMillis();
        loginUser.setLastLoginTime(curMillis);
        loginUser.setExpireTime(curMillis + expireTime * MILLIS_MINUTE);

        // 缓存用户信息
        String key = getUserCacheKey(loginUser.getUuid());
        redisHandler.set(key, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 构建Token
     *
     * @param claims 存放在Token中的数据
     * @return Token
     */
    public String createToken(Map<String, Object> claims) {
        // 构建器
        return Jwts.builder()
                // 设置主体
                .setClaims(claims)
                // 加密方式: 好色512 & 秘钥
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取Token的载荷
     *
     * @param token 令牌
     * @return 载荷
     */
    private Claims parseTokenStr(String token) {
        // 解析器
        return Jwts.parser()
                // 设置加密密码
                .setSigningKey(secret)
                // 解析TOKEN
                .parseClaimsJws(token)
                // 获取主体
                .getBody();
    }

    /**
     * 清理缓存
     */
    public void deleteCacheUser(LoginUser loginUser) {
        if (Objects.nonNull(loginUser) && Objects.nonNull(loginUser.getUuid())) {
            String userCacheKey = getUserCacheKey(loginUser.getUuid());
            redisHandler.delete(userCacheKey);
        }
    }

}
