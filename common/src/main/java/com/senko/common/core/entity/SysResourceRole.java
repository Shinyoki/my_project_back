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
 * 资源角色映射实体类
 *
 * @author senko
 * @date 2022/9/16 20:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_resource_role")
public class SysResourceRole {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 角色ID
     */
    private Long roleId;

}
