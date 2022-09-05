package com.senko.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 角色 映射
 *
 * @author senko
 * @date 2022/9/4 21:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_user_role")
public class SysUserRole {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 系统用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
