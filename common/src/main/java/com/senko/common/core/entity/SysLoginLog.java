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

import java.time.LocalDateTime;

/**
 * 登录日志PO
 *
 * @author senko
 * @date 2022/9/29 22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("登录日志")
@TableName("tb_login_log")
public class SysLoginLog {

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 登录的用户名
     */
    @ApiModelProperty(value = "登录的用户名")
    private String username;

    /**
     * 登录的密码
     */
    @ApiModelProperty(value = "登录的密码")
    private String password;

    /**
     * 登录的IP
     */
    @ApiModelProperty(value = "登录的IP")
    private String ip;

    /**
     * 登录地址
     */
    @ApiModelProperty(value = "登录地址")
    private String location;

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

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String message;

    /**
     * 成功与否 0：失败 1：成功
     */
    @ApiModelProperty(value = "成功与否 0：失败 1：成功")
    private Integer status;

    /**
     * 登录的时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "登录的时间")
    private LocalDateTime createTime;

}
