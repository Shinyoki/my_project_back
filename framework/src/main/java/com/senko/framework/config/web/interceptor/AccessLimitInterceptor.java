package com.senko.framework.config.web.interceptor;

import com.senko.common.annotations.AccessLimit;
import com.senko.common.constants.RedisConstants;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.utils.ip.IpUtils;
import com.senko.framework.config.redis.RedisHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 限流，redis实现
 *
 * @author senko
 * @date 2022/9/10 15:40
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);

    @Autowired
    private RedisHandler redisHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 如果是资源Get/Post...Mapping请求
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 提取注解，得到参数
            AccessLimit limitAnno = handlerMethod.getMethod().getAnnotation(AccessLimit.class);
            if (Objects.nonNull(limitAnno)) {
                // 该资源方法被限流了
                int maxLimit = limitAnno.counts();
                int seconds = limitAnno.seconds();

                // redis key
                String ip = IpUtils.getHostIp();
                String methodName = handlerMethod.getMethod().getName();
                String redisKey = RedisConstants.ACCESS_LIMIT_PREFIX + ip + methodName;

                try {
                    Long requestCount = redisHandler.incrementAndExpire(redisKey, seconds);
                    if (requestCount > maxLimit) {
                        // 大于这个数，则不通过
                        logger.warn("IP：{},请求：{}方法超过最大{}次数", ip, methodName, maxLimit);
                        return false;
                    }
                } catch (Exception e) {
                    logger.error("为IP：{} 的请求：{}方法添加限流时遭遇Redis数据库异常!", ip, methodName);
                    throw new ServiceException("链接Redis数据库异常!", e);
                }

            }
        }
        // 访问通过
        return true;
    }

}
