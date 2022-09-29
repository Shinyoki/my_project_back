package com.senko.common.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author senko
 * @date 2022/9/27 22:18
 */
@ApiModel("操作日志VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationLogVO {

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private String username;

    /**
     * 所属模块
     */
    @ApiModelProperty("所属模块")
    private String module;

    /**
     * 操作状态
     */
    @ApiModelProperty("操作状态,0：失败，1：成功")
    private String status;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

}
