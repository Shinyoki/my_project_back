package com.senko.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 资源、所需角色DTO
 *
 * @author senko
 * @date 2022/9/5 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("资源、所需角色DTO")
public class ResourceRoleDTO {

    /**
     * 资源ID
     */
    @ApiModelProperty("资源ID")
    private Integer id;

    /**
     * 请求资源
     */
    @ApiModelProperty("请求资源")
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式")
    private String method;

    /**
     * 所需角色集合
     */
    @ApiModelProperty("所需角色集合")
    private Set<String> roles;

}
