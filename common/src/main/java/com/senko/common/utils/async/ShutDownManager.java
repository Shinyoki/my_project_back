package com.senko.common.utils.async;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 应用关闭时自动执行@PreDestroy方法来确保关闭后台线程
 *
 * @author senko
 * @date 2022/9/30 7:40
 */
@Component
public class ShutDownManager {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @PreDestroy
    public void destroy() {
        try {
            logger.info("====关闭后台任务任务线程池====");
            AsyncManager.getInstance().shutdown();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
