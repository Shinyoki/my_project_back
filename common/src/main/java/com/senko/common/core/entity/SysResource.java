package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作、请求资源
 *
 * @author senko
 * @date 2022/8/31 15:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_resource")
public class SysResource {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 资源名，如 获取用户
     */
    private String resourceName;

    /**
     * 资源地址，如 /user
     */
    private String url;

    /**
     * 资源请求方法，如 GET
     */
    private String method;

    /**
     * 资源所属模块（父）ID
     */
    private Long parentId;

    /**
     * 是否匿名可访问 0：需认证，1：随便访问
     */
    private Integer isAnonymous;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
