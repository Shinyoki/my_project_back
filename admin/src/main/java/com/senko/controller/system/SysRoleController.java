package com.senko.controller.system;

import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.Result;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.common.core.vo.RoleIsDisabledVO;
import com.senko.framework.web.core.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author senko
 * @date 2022/9/12 10:21
 */
@RestController
@Api(tags = "角色Controller")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    /**
     * 查询后台角色集合
     *
     * @param roleName   角色名称
     * @param roleLabel  角色标签
     * @param isDisabled 是否被禁用
     */
    @GetMapping("/admin/roles")
    @ApiOperation("获取后台角色集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleLabel", value = "角色标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isDisabled", value = "是否被禁用，0：正常，1：禁用", dataType = "Integer", paramType = "query")
    })
    public Result<PageResult<SysRoleDTO>> listBackRoleList(@RequestParam(value = "name", required = false) String roleName,
                                                           @RequestParam(value = "label", required = false) String roleLabel,
                                                           @RequestParam(value = "isDisabled", required = false) Integer isDisabled) {

        PageResult<SysRoleDTO> roleDTOS = roleService.listBackRoleList(roleName, roleLabel, isDisabled);
        return Result.ok("查询角色成功！", roleDTOS);

    }

    /**
     * 查询 角色标签集合
     */
    @ApiOperation("获取角色标签集合")
    @GetMapping("/admin/roles/labels")
    private Result<List<SysRoleDTO>> listRoleLabels() {
        return Result.ok(roleService.listRoleLabels());
    }

    /**
     * 查询 菜单的角色标签集合
     */
    @ApiOperation("查询菜单的角色标签集合")
    @GetMapping("/admin/menu/{menuId}/roles")
    public Result<List<SysRoleDTO>> listMenuRoles(@PathVariable("menuId") Long menuId) {
        return Result.ok(roleService.listMenuRoles(menuId));
    }

    /**
     * 更新角色的禁用状态
     * @param roleId        角色ID
     * @param isDisabled    角色禁用状态
     */
    @ApiOperation("更新角色的禁用状态")
    @PutMapping("/admin/role/disable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true, value = "角色ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "isDisabled", required = true, value = "是否被禁用，0：正常，1：禁用", dataType = "Integer", paramType = "query")
    })
    public Result<?> updateRoleIsDisabled(@RequestBody @Valid RoleIsDisabledVO roleIsDisabledVO) {
        roleService.updateRoleIsDisabled(roleIsDisabledVO.getRoleId(), roleIsDisabledVO.getIsDisabled());
        return Result.ok("更新角色的禁用状态成功！");
    }

    /**
     * 删除角色
     * @param roleIds   角色ID集合
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/admin/roles")
    @ApiImplicitParam(name = "roleIds", required = true, value = "角色ID集合", dataType = "Long", paramType = "query")
    public Result<?> deleteBathByIds(@RequestBody @NotNull(message = "不能为null") List<Long> roleIds) {
        roleService.deleteBathByIds(roleIds);
        return Result.ok("删除角色成功！");
    }
    // TODO 删除  更新Disabled状态

}
