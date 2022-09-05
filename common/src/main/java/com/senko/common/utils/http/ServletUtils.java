package com.senko.common.utils.http;

import com.senko.common.constants.CommonConstants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 请求工具类
 *
 * @author senko
 * @date 2022/8/26 19:50
 */
public class ServletUtils {

    /**
     * 获取当前线程的RequestAttribute，那肯定是Spring封装过的ServletRequestAttributes
     *
     * Spring-context-support
     */
    public static ServletRequestAttributes getRequestAttributes() {

        return  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    }

    /**
     * 获取当前的请求
     */
    public static HttpServletRequest getRequest() {

        return getRequestAttributes()
                .getRequest();

    }

    /**
     * 获取当前请求的会话
     */
    public static HttpSession getSession() {

        return getRequest()
                .getSession();

    }

    /**
     * 获取当前的响应
     */
    public static HttpServletResponse getResponse() {

        return getRequestAttributes()
                .getResponse();

    }


    /**
     * 强制返回渲染JSON数据
     */
    public static void renderJSONResult(String message) {

        HttpServletResponse response = getResponse();
        // 设置响应格式
        response.setContentType(CommonConstants.HTTP_CONTENT_TYPE_JSON);
        response.setCharacterEncoding(CommonConstants.ENCODING_UTF8);
        response.setStatus(200);

        try {
            // 输出响应内容
            response.getWriter()
                    .write(message);
        } catch (IOException ignored) {
        }

    }

}
