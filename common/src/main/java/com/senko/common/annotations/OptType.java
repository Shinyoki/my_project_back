package com.senko.common.annotations;

/**
 * 操作类型
 *
 * @author senko
 * @date 2022/8/26 19:45
 */
public enum OptType {

    /**
     * 请求
     */
    QUERY("QUERY"),

    /**
     * 插入
     */
    SAVE("SAVE"),

    /**
     * 更新或插入
     */
    SAVE_OR_UPDATE("SAVE_OR_UPDATE"),

    /**
     * 更新
     */
    UPDATE("UPDATE"),

    /**
     * 删除
     */
    REMOVE("REMOVE");
    private String method;

    OptType(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
