package com.senko.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 在线用户信息DTO
 *
 * @author senko
 * @date 2022/9/27 11:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("在线用户信息DTO")
public class OnlineUserDTO {

    /**
     * 会话编号
     */
    @ApiModelProperty("会话编号")
    private String sessionUID;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String ip;

    /**
     * 登录地址
     */
    @ApiModelProperty("登录地址")
    private String location;

    /**
     * 浏览器
     */
    @ApiModelProperty("浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String os;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    private Long loginTime;

}
