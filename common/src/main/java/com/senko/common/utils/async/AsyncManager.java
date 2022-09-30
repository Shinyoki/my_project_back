package com.senko.common.utils.async;

import com.senko.common.utils.bean.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 提交异步任务
 *
 * @author senko
 * @date 2022/9/29 23:25
 */
public class AsyncManager {

    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    private ScheduledExecutorService executorService = SpringUtils.getBean("scheduledExecutorService");

    private AsyncManager() {}

    private static final AsyncManager asyncManager = new AsyncManager();

    public static AsyncManager getInstance() {
        return asyncManager;
    }

    /**
     * 执行一次任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executorService.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        executorService.shutdown();
    }

}
