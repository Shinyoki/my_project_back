package com.senko.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求参数封装VO
 *
 * @author senko
 * @date 2022/9/11 8:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("请求参数封装VO")
public class RequestParamsVO {

    /**
     * 关键词，可以是用户名、邮箱、手机号等
     */
    @ApiModelProperty("关键词，可以是用户名、邮箱、手机号等")
    private String keywords;

    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Long current;

    /**
     * 每页显示的条数
     */
    @ApiModelProperty("每页显示的条数")
    private Long size;

    // ======= 拓展 ========

    /**
     * 是否隐藏 0-否 1-是
     */
    @ApiModelProperty("是否隐藏，0：否，1：是")
    private Integer isHidden;

}
