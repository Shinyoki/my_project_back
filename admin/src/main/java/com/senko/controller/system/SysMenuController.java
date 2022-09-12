package com.senko.controller.system;

import com.senko.common.core.dto.SysMenusDTO;
import com.senko.common.core.entity.Result;
import com.senko.common.core.vo.RequestParamsVO;
import com.senko.framework.web.core.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单Controller
 *
 * @author senko
 * @date 2022/9/11 21:17
 */
@RestController
@Api(tags = "菜单Controller")
public class SysMenuController {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取后台路由信息
     *
     * @param requestParamsVO 请求参数
     * @return 路由信息
     */
    @ApiOperation("获取后台路由信息")
    @GetMapping("/admin/menus")
    public Result<List<SysMenusDTO>> listBackMenus(RequestParamsVO requestParamsVO) {

        List<SysMenusDTO> result = menuService.listBackMenus(requestParamsVO);
        return Result.ok("查询菜单成功！", result);

    }

    /**
     * 删除特定菜单
     * <p>
     * 删除目录也可以，但是为了省事，不做递归删除
     *
     * @param menuId 菜单ID
     */
    @ApiOperation("删除菜单")
    @DeleteMapping("/admin/menu/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Long menuId) {

        menuService.deleteMenu(menuId);
        return Result.ok("删除菜单成功！");

    }

}
