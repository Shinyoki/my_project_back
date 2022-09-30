package com.senko.common.core.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 *
 * @author senko
 * @date 2022/9/15 19:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询结果")
public class PageResult<T> {

    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Integer total;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private List<T> records;

    public PageResult(List<T> records) {
        this.records = records;
        this.total = records.size();
    }

    public PageResult(Page<T> page) {
        this.total = (int)page.getTotal();
        this.records = page.getRecords();
    }
}
