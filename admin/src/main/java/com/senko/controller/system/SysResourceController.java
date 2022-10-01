package com.senko.controller.system;

import com.senko.common.annotations.LogOperation;
import com.senko.common.annotations.OptType;
import com.senko.common.core.dto.SysRoleDTO;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysResource;
import com.senko.common.core.vo.ResourceVO;
import com.senko.framework.web.core.service.ISysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 资源Controller
 *
 * @author senko
 * @date 2022/9/30 9:03
 */
@Api(tags = "资源Controller")
@RestController
@RequestMapping("/admin/resource")
public class SysResourceController {

    @Autowired
    private ISysResourceService resourceService;

    /**
     * 获取资源的树形列表
     */
    @GetMapping("/tree")
    public Result<List<SysResource>> listResourceTree(ResourceVO resourceVO) {
        return Result.ok(resourceService.listResourceTree(resourceVO));
    }

    /**
     * 获取资源的列表
     */
    @LogOperation(OptType.UPDATE)
    @ApiOperation("更新资源匿名状态")
    @PutMapping("/anonymous/{status}")
    public Result<?> updateAnonymousStatus(@PathVariable("status") @Valid @Min(0) @Max(1) Integer isAnonymous, @RequestBody ResourceVO resourceVO) {
        resourceService.updateAnonymousStatus(resourceVO, isAnonymous);
        return Result.ok("更新匿名状态成功");
    }

    /**
     * 删除资源
     */
    @LogOperation(OptType.REMOVE)
    @ApiOperation("删除资源")
    @DeleteMapping("/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") @Valid @Min(1) Long resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok("删除资源成功");
    }

    /**
     * 保存或更新资源
     * @param resourceVO    资源VO
     */
    @LogOperation(OptType.SAVE_OR_UPDATE)
    @ApiOperation("保存或更新资源")
    @PostMapping
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok("操作成功");
    }

    /**
     * 查询该资源的角色
     * @param resourceId    资源ID
     */
    @ApiOperation("查询该资源的角色")
    @GetMapping("/{resourceId}/roles")
    public Result<List<SysRoleDTO>> listResourceRoles(@PathVariable("resourceId") Long resourceId) {
        return Result.ok("查询成功", resourceService.listResourceRoles(resourceId));
    }

}
