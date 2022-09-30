package com.senko.common.utils.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring IOC工具类
 *
 * @author senko
 * @date 2022/9/30 7:32
 */
@Component
@SuppressWarnings("unchecked")
public class SpringUtils implements ApplicationContextAware {

    /**
     * ApplicationContext缓存
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 根据名字获取对应Bean
     */
    public static <T> T getBean(String name) {
        return (T)applicationContext.getBean(name);
    }

    /**
     * 根据类型获取对应Bean
     * @param clazz
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
