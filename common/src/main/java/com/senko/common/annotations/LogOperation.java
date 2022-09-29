package com.senko.common.annotations;

import java.lang.annotation.*;

/**
 * 记录日志切面注解
 *
 * 只用在 已登录用户 才操作的注解上
 * @author senko
 * @date 2022/8/26 19:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface LogOperation {

    /**
     * 操作类型
     */
    OptType value() default OptType.QUERY;

}

