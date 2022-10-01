package com.senko.framework.strategy.context;

import com.senko.framework.strategy.AbstractUploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 上传策略上下文
 *
 * @author senko
 * @date 2022/10/1 8:22
 */
@Component
public class FileUploadStrategyContext {

    public enum UploadStrategy {
        /**
         * 本地存储
         */
        LOCAL("localUploadStrategy");

        private final String strategyBeanName;

        UploadStrategy(String strategyBeanName) {
            this.strategyBeanName = strategyBeanName;
        }

        public String getStrategyBeanName() {
            return strategyBeanName;
        }

    }


    /**
     * 上传策略 桶
     */
    @Autowired
    private Map<String, AbstractUploadStrategy> uploadStrategyMap;

    /**
     * 获取策略
     */
    public AbstractUploadStrategy getStrategy(UploadStrategy strategy) {
        return uploadStrategyMap.get(strategy.getStrategyBeanName());
    }

}
