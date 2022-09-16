package com.senko.common.core.dto;

import com.senko.common.core.entity.SysMenuTree;
import com.senko.common.core.entity.SysResourceTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色 菜单/资源 DTO
 *
 * @author senko
 * @date 2022/9/16 15:00
 */
@ApiModel("角色 菜单/资源 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleMenuResourceDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * 选中的IDS，可能是 menu的id，也可能是 resource的id
     */
    @ApiModelProperty(value = "选中的IDS，可能是 menu的id，也可能是 resource的id")
    private List<Long> checkedIds;

    /**
     * 菜单树封装
     */
    @ApiModelProperty(value = "菜单树封装")
    private List<SysMenuTree> menuTree;

    /**
     * 资源树封装
     */
    @ApiModelProperty(value = "资源树封装")
    private List<SysResourceTree> resourceTree;

}
