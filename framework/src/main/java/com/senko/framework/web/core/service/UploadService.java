package com.senko.framework.web.core.service;

import com.senko.common.constants.UploadConstants;
import com.senko.common.core.entity.SysUserInfo;
import com.senko.common.exceptions.service.ServiceException;
import com.senko.framework.config.security.SecurityUtils;
import com.senko.framework.strategy.context.FileUploadStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传服务
 *
 * @author senko
 * @date 2022/10/1 15:00
 */
@Service
public class UploadService {

    @Autowired
    private ISysUserInfoService userInfoService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private FileUploadStrategyContext fileUploadStrategyContext;

    /**
     * 更新用户头像
     * @param file  图片文件
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateUserAvatar(MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new ServiceException("上传图片不能为空");
        }
        // 检查文件类型是否为图片
        String contentType = file.getContentType();
        if (!contentType.startsWith(UploadConstants.IMAGE_PREFIX)) {
            throw new ServiceException("文件类型错误");
        }

        String url = fileUploadStrategyContext.getStrategy(FileUploadStrategyContext.UploadStrategy.LOCAL)
                .doUpload(UploadConstants.PHOTO_PATH, file);
        Long userInfoId = SecurityUtils.getLoginUser().getUserInfoId();
        SysUserInfo entity = SysUserInfo.builder()
                .id(userInfoId)
                .avatar(url)
                .build();
        userInfoService.updateById(entity);
        return url;
    }

}
