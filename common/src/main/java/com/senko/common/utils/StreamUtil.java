package com.senko.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Objects;

/**
 * 流处理工具
 *
 * @author senko
 * @date 2022/10/1 8:35
 */
public class StreamUtil {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 关闭流
     * @param closeables    流
     */
    public static void closeAll(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                if (Objects.nonNull(closeable)) {
                    closeable.close();
                }
            } catch (Exception e) {
                logger.error("关闭流失败", e);
            }
        }
    }

}
