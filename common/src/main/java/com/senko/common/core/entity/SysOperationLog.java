package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.time.LocalDateTime;

/**
 * 操作日志PO
 *
 * @author senko
 * @date 2022/9/27 22:05
 */
@ApiModel("操作日志")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_operation_log")
public class SysOperationLog {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId
    private Long id;

    /**
     * 操作人ID
     */
    @ApiModelProperty(value = "操作人ID")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 操作人IP
     */
    @ApiModelProperty(value = "操作人IP")
    private String ip;

    /**
     * 操作地区
     */
    @ApiModelProperty("操作地区")
    private String location;

    /**
     * 操作所属模块
     */
    @ApiModelProperty(value = "操作所属模块")
    private String module;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String type;

    /**
     * 资源地址
     */
    @ApiModelProperty(value = "资源地址")
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String method;

    /**
     * 处理方法
     */
    @ApiModelProperty(value = "处理方法")
    private String handler;

    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    private String des;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 请求结果
     */
    @ApiModelProperty(value = "请求结果")
    private String result;

    /**
     * 请求状态
     */
    @ApiModelProperty(value = "请求状态，0：失败，1：成功")
    private Integer status;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String message;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @TableField(fill = FieldFill.INSERT,typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime createTime;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String os;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    private String browser;

}
