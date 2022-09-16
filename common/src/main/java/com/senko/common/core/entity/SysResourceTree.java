package com.senko.common.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 资源树封装
 *
 * @author senko
 * @date 2022/9/16 20:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("资源树封装")
public class SysResourceTree {

    /**
     * 资源ID
     */
    @ApiModelProperty(value = "资源ID")
    private Long id;

    /**
     * 资源名
     */
    @ApiModelProperty(value = "资源名")
    private String label;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    /**
     * 子资源
     */
    @ApiModelProperty("子资源")
    private List<SysResourceTree> children;

}
