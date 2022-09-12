package com.senko.controller.system;

import com.senko.common.core.entity.Result;
import com.senko.framework.web.core.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询 角色标签集合
     */
    @ApiOperation("获取角色标签集合")
    @GetMapping("/admin/roles/labels")
    private Result<List<String>> listRoleLabels() {
        return Result.ok(roleService.listRoleLabels());
    }

}
