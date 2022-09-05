package com.senko.framework.config.mysql;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 元对象处理器
 *
 * @author senko
 * @date 2022/8/31 13:58
 */
@Configuration
public class MybatisMetaObjectConfig implements MetaObjectHandler {

    /**
     * 字段插入处理
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(ZoneId.of("Asia/Shanghai")), metaObject);
        this.setFieldValByName("createTime", LocalDateTime.now(ZoneId.of("Asia/Shanghai")), metaObject);
    }

    /**
     * 字段更新处理
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(ZoneId.of("Asia/Shanghai")), metaObject);
    }

}
