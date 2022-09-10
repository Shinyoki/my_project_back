package com.senko.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AccessLimit {

    /**
     * 每区间内可访问次数
     */
    int counts();

    /**
     * 限制区间 单位秒
     */
    int seconds();

}
