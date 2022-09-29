package com.senko.controller.system;

import com.senko.common.annotations.LogOperation;
import com.senko.common.annotations.OptType;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.dto.SysRoleMenuResourceDTO;
import com.senko.common.core.dto.SysUserAssignmentDTO;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.Result;
import com.senko.common.core.vo.RoleAssignmentVO;
import com.senko.common.core.vo.RoleIsDisabledVO;
import com.senko.common.core.vo.SysRoleVO;
import com.senko.framework.web.core.service.ISysRoleService;
import com.senko.framework.web.core.service.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * @author senko
 * @date 2022/9/12 10:21
 */
@RestController
@Api(tags = "角色Controller")
public class SysRoleController {

    private final ISysRoleService roleService;

    private final ISysUserRoleService userRoleService;

    @Autowired
    public SysRoleController(ISysRoleService roleService, ISysUserRoleService userRoleService) {
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }


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
    public Result<PageResult<SysRoleDTO>> listBackRoleList(@RequestParam(value = "roleName", required = false) String roleName,
                                                           @RequestParam(value = "roleLabel", required = false) String roleLabel,
                                                           @RequestParam(value = "isDisabled", required = false) Integer isDisabled) {

        PageResult<SysRoleDTO> roleDTOS = roleService.listBackRoleList(roleName, roleLabel, isDisabled);
        return Result.ok("查询角色成功！", roleDTOS);

    }

    /**
     * 查询 角色标签集合
     */
    @ApiOperation("获取角色标签集合")
    @GetMapping("/admin/roles/labels")
    public Result<List<SysRoleDTO>> listRoleLabels() {
        List<SysRoleDTO> roleDTOS = roleService.listRoleLabels();
        return Result.ok(roleDTOS);
    }

    /**
     * 查询 菜单的角色标签集合
     */
    @ApiOperation("查询菜单的角色标签集合")
    @GetMapping("/admin/menu/{menuId}/roles")
    public Result<List<SysRoleDTO>> listMenuRoles(@PathVariable("menuId") Long menuId) {
        List<SysRoleDTO> roleDTOS = roleService.listMenuRoles(menuId);
        return Result.ok(roleDTOS);
    }

    /**
     * 更新角色的禁用状态
     */
    @LogOperation(OptType.UPDATE)
    @ApiOperation("更新角色的禁用状态")
    @PutMapping("/admin/role/disable")
    public Result<?> updateRoleIsDisabled(@RequestBody @Valid RoleIsDisabledVO roleIsDisabledVO) {
        roleService.updateRoleIsDisabled(roleIsDisabledVO.getRoleId(), roleIsDisabledVO.getIsDisabled());
        return Result.ok("更新角色的禁用状态成功！");
    }

    /**
     * 删除角色
     * @param roleIds   角色ID集合
     */
    @LogOperation(OptType.REMOVE)
    @ApiOperation("删除角色")
    @DeleteMapping("/admin/roles")
    @ApiImplicitParam(name = "roleIds", required = true, value = "角色ID集合", dataType = "Long", paramType = "query")
    public Result<?> deleteBathByIds(@RequestBody @NotNull(message = "不能为null") List<Long> roleIds) {
        roleService.deleteBathByIds(roleIds);
        return Result.ok("删除角色成功！");
    }

    /**
     * 获取角色的菜单封装
     * @param roleId        角色ID
     */
    @ApiOperation("获取角色的菜单封装")
    @GetMapping("/admin/role/menus/{roleId}")
    public Result<SysRoleMenuResourceDTO> listRoleBackMenus(@PathVariable("roleId") Long roleId) {
        SysRoleMenuResourceDTO roleMenuResourceDTOS = roleService.listRoleBackMenus(roleId);
        return Result.ok("查询角色的菜单封装成功！", roleMenuResourceDTOS);
    }

    @ApiOperation("获取角色的资源封装")
    @GetMapping("/admin/role/resources/{roleId}")
    public Result<SysRoleMenuResourceDTO> listRoleBackResources(@PathVariable("roleId") Long roleId) {
        SysRoleMenuResourceDTO roleMenuResourceDTOS = roleService.listRoleBackResources(roleId);
        return Result.ok("查询角色的资源封装成功！", roleMenuResourceDTOS);
    }

    /**
     * 新增或修改角色，包括其可访资源/菜单
     */
    @LogOperation(OptType.SAVE_OR_UPDATE)
    @ApiOperation("新增或修改角色，包括其可访资源/菜单")
    @PostMapping("/admin/role")
    public Result<?> saveOrUpdateRole(@Valid @RequestBody SysRoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.ok("操作成功！");
    }

    /**
     * 获取授权角色 集合
     * @param assignmentVO  参数：角色ID，用户名、昵称、状态、、
     */
    @ApiOperation("获取授权的角色")
    @GetMapping("/admin/role/assignment")
    public Result<PageResult<SysUserAssignmentDTO>> listSysUserAssignmentDTOs(@Valid RoleAssignmentVO assignmentVO) {
        return Result.ok(roleService.listRoleAssignmentList(assignmentVO));
    }

    /**
     * 删除角色授权的用户
     * @param roleId    角色ID
     * @param userIds   用户ID集合
     */
    @LogOperation(OptType.REMOVE)
    @ApiOperation("删除角色授权的用户")
    @DeleteMapping("/admin/role/assignment/{roleId}")
    public Result<?> deleteRoleAssignment(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIds) {
        roleService.deleteRoleAssignment(roleId, userIds);
        return Result.ok("删除授权的角色成功！");
    }

    /**
     * 查询未授权用户
     */
    @ApiOperation("查询未授权用户")
    @GetMapping("/admin/role/unassignment")
    public Result<PageResult<SysUserAssignmentDTO>> listUnAssignmentUsers(RoleAssignmentVO assignmentVO) {
        return Result.ok(roleService.listUnAssignmentUsers(assignmentVO));
    }

    /**
     * 新增角色授权的用户
     * @param roleId    角色ID
     * @param userIds   用户ID集合
     */
    @LogOperation(OptType.SAVE)
    @ApiOperation("新增角色授权的用户")
    @PostMapping("/admin/role/assignment/{roleId}")
    public Result<?> saveRoleAssignment(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIds) {
        userRoleService.saveRoleAssignment(roleId, userIds);
        return Result.ok("新增授权的角色成功！");
    }

    /**
     * 获取该用户的角色信息
     * @param userId    用户ID
     */
    @ApiOperation("获取该用户的角色信息")
    @GetMapping("/admin/user/{userId}/role")
    public Result<SysRoleDTO> getRoleByUserId(@PathVariable("userId") Long userId) {
        return Result.ok(roleService.getRoleByUserId(userId));
    }

}
