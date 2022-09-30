package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
@ApiModel(value = "资源")
public class SysResource {

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 资源名，如 获取用户
     */
    @ApiModelProperty(value = "资源名，如 获取用户")
    private String resourceName;

    /**
     * 资源地址，如 /user
     */
    @ApiModelProperty(value = "资源地址，如 /user")
    private String url;

    /**
     * 资源请求方法，如 GET
     */
    @ApiModelProperty(value = "资源请求方法，如 GET")
    private String method;

    /**
     * 资源所属模块（父）ID
     */
    @ApiModelProperty(value = "资源所属模块（父）ID")
    private Long parentId;

    /**
     * 是否匿名可访问 0：需认证，1：随便访问
     */
    @ApiModelProperty(value = "是否匿名可访问 0：需认证，1：随便访问")
    private Integer isAnonymous;

    /**
     * 资源类型 0：模块，1：接口
     */
    @ApiModelProperty(value = "资源类型 0：模块，1：接口")
    private Integer resourceType;

    /**
     * 子资源
     */
    @ApiModelProperty(value = "子资源")
    @TableField(exist = false)
    private List<SysResource> children;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
