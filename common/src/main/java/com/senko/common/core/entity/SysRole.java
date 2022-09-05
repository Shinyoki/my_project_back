package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色
 *
 * @author senko
 * @date 2022/8/31 15:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_role")
public class SysRole {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色
     */
    private String roleLabel;

    /**
     * 禁用 0：正常 1：禁用
     */
    private Integer isDisabled;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
