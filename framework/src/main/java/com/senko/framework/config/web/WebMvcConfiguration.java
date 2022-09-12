package com.senko.framework.config.web;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.senko.framework.config.web.interceptor.AccessLimitInterceptor;
import com.senko.framework.config.web.interceptor.PageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;

/**
 * WebMvc的配置类
 *
 * @author senko
 * @date 2022/9/10 15:52
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Autowired
    private PageInterceptor pageInterceptor;

    /**
     * 把Long类型转换成String类型，
     * 防止Axios接受的Long出现精度丢失
     */
    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializerByType(BigInteger.class, ToStringSerializer.instance)
                .serializerByType(Long.class,ToStringSerializer.instance)
                .serializerByType(Long.TYPE,ToStringSerializer.instance);
    }

    /**
     * 限流配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 添加限流拦截器
        registry.addInterceptor(accessLimitInterceptor);
        // 分页拦截器
        registry.addInterceptor(pageInterceptor);

    }

    /**
     * 添加跨域的设置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 为所有的请求地址添加相同的跨域规则
        registry.addMapping("/**")
                // 允许携带验证信息头
                .allowCredentials(true)
                // 允许的headers
                .allowedHeaders("*")
                // 允许的域
                .allowedOrigins("*")
                // 允许的方法
                .allowedMethods("*");

    }

}
