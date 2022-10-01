package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 资源VO
 *
 * @author senko
 * @date 2022/9/30 10:23
 */
@ApiModel("资源VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVO {

    /**
     * 资源ID
     */
    @ApiModelProperty(value = "资源ID", required = true)
    private Long id;

    /**
     * 资源名
     */
    @ApiModelProperty(value = "资源名",required = true)
    @NotBlank(message = "资源名不能为空")
    private String resourceName;

    /**
     * 资源地址
     */
    @ApiModelProperty(value = "资源地址",required = true)
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式",required = true)
    private String method;

    /**
     * 是否匿名访问
     */
    @ApiModelProperty(value = "是否匿名访问，0:不可匿名访问，1:可匿名访问",required = true)
    @Min(value = 0, message = "匿名状态不合法")
    @Max(value = 1, message = "匿名状态不合法")
    @NotNull(message = "匿名状态不能为空")
    private Integer isAnonymous;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID,顶级资源ID为0",required = true)
    @Min(value = 0, message = "父ID不合法")
    @NotNull(message = "父ID不能为空")
    private Long parentId;

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型，0:模块，1:接口",required = true)
    @NotNull(message = "资源类型不能为空")
    private Integer resourceType;

    /**
     * 可操作的角色ID列表
     */
    @ApiModelProperty(value = "可操作角色ID列表",required = true)
    private List<Long> roleIds;

}
