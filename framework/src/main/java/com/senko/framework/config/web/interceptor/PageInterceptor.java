package com.senko.framework.config.web.interceptor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.senko.common.exceptions.service.PageRequestException;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.utils.page.PageUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static com.senko.common.constants.CommonConstants.*;

/**
 * 分页拦截器
 *
 * 每次请求都检查是否携带 和 分页有关的参数，
 * 如果有就自动封装并存储到ThreadLocal中
 *
 * @author senko
 * @date 2022/9/10 15:57
 */
@Component
public class PageInterceptor implements HandlerInterceptor {

    /**
     * 只进行前置处理，封装Page参数，不需要考虑是否放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 请求方法
            String strCurrent = request.getParameter(PAGE_CURRENT);
            String strSize = request.getParameter(PAGE_SIZE);

            if (Objects.nonNull(strCurrent)) {
                try {
                    // 提取参数
                    long current = Long.parseLong(strCurrent);
                    long size = 0L;
                    if (Objects.isNull(strSize)) {
                        size = PAGE_SIZE_VALUE;
                    } else {
                        size = Long.parseLong(strSize);
                    }

                    PageUtils.setPage(new Page<>(current, size));
                } catch (NumberFormatException e) {
                    throw new PageRequestException("请求分页参数错误，请检查分页参数值的格式是否正确");
                }

            }
        }
        return true;
    }

    /**
     * 后置处理，放置内存泄漏
     * (毕竟开那么多线程，每开一次都设置一个Page)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        PageUtils.removePage();
    }
}
