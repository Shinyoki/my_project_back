package com.senko.controller.system;

import com.senko.common.constants.UploadConstants;
import com.senko.common.core.entity.Result;
import com.senko.framework.strategy.context.FileUploadStrategyContext;
import com.senko.framework.web.core.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传Controller
 *
 * @author senko
 * @date 2022/10/1 10:51
 */
@Api(tags = "上传模块")
@RestController
@RequestMapping("/upload")
public class SysUploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 更新用户头像
     * @param file  文件
     * @return      {@link Result<String>} 图片地址
     */
    @ApiOperation("上传图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/photo/user/avatar")
    public Result<?> uploadPhoto(MultipartFile file) {

        String url = uploadService.updateUserAvatar(file);
        return Result.ok("上传成功", url);
    }

}
