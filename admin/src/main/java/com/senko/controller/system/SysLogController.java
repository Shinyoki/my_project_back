package com.senko.controller.system;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.senko.common.annotations.LogOperation;
import com.senko.common.annotations.OptType;
import com.senko.common.core.entity.PageResult;
import com.senko.common.core.entity.Result;
import com.senko.common.core.entity.SysLoginLog;
import com.senko.common.core.entity.SysOperationLog;
import com.senko.common.core.vo.LogVO;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.framework.web.core.service.ISysLoginLogService;
import com.senko.framework.web.core.service.ISysOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 日志
 *
 * @author senko
 * @date 2022/9/27 21:49
 */
@Api(tags = "系统日志")
@RestController
public class SysLogController {

    @Autowired
    private ISysOperationLogService operationLogService;

    @Autowired
    private ISysLoginLogService loginLogService;

    /**
     * 查询操作日志列表
     * @param logVO    请求参数
     */
    @ApiOperation("操作日志")
    @GetMapping("/admin/log/operation")
    public Result<PageResult<SysOperationLog>> listOperationLogDTOList(LogVO logVO) {
        return Result.ok("查询成功", operationLogService.listOperationLogDTOList(logVO));
    }

    /**
     * 查询登录日志列表
     * @param logVO   请求参数
     */
    @ApiOperation("获取登录日志")
    @GetMapping("/admin/log/login")
    public Result<PageResult<SysLoginLog>> listLoginLogDTOList(LogVO logVO) {
        return Result.ok("查询成功", loginLogService.listLoginLogDTOList(logVO));
    }

    /**
     * 删除操作日志
     * @param logIds    日志id集合
     */
    @ApiOperation("删除操作日志")
    @DeleteMapping("/admin/log/operation")
    public Result<?> deleteOperationLog(@RequestBody List<Long> logIds) {
        if (CollectionUtils.isEmpty(logIds)) {
            throw new ServiceException("日志id不能为空");
        }
        operationLogService.removeBatchByIds(logIds);
        return Result.ok("删除成功");
    }

    /**
     * 删除登录日志
     * @param logIds    日志id集合
     */
    @ApiOperation("删除登录日志")
    @DeleteMapping("/admin/log/login")
    public Result<?> deleteLoginLog(@RequestBody List<Long> logIds) {
        if (CollectionUtils.isEmpty(logIds)) {
            throw new ServiceException("日志id不能为空");
        }
        loginLogService.removeBatchByIds(logIds);
        return Result.ok("删除成功");
    }

}
