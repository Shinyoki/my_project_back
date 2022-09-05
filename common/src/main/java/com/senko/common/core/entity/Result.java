package com.senko.common.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用返回结果
 *
 * @author senko
 * @date 2022/9/3 17:09
 */
@Data
@AllArgsConstructor
public class Result<T> {

    /**
     * 直接了断的返回状态
     * true or false
     */
    private Boolean status;

    /**
     * Http状态码
     * 200：正常、400：参数错误、401：未认证、403：无权限、404：未找到、500：服务器错误
     */
    private Integer code;

    /**
     * 人性化消息反馈
     */
    private String message;

    /**
     * 承载数据
     */
    private T data;

    // ==================== OK ======================
    public static <T> Result<T> ok() {
        return new Result<>(true, 200, "操作成功", null);
    }

    public static <T> Result<T> ok(String message) {
        return new Result<>(true, 200, message, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(true, 200, "操作成功", data);
    }

    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(true, 200, message, data);
    }

    // ==================== ERROR ======================
    public static <T> Result<T> error() {
        return new Result<>(false, 500, "操作失败", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(false, 500, message, null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(false, code, message, null);
    }

}
