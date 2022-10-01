package com.senko.framework.strategy.impl.upload;

import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.utils.StreamUtil;
import com.senko.framework.config.SenkoConfig;
import com.senko.framework.strategy.AbstractUploadStrategy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;

/**
 * 本地存储的上传策略
 *
 * @author senko
 * @date 2022/10/1 9:09
 */
@Component("localUploadStrategy")
public class LocalUploadStrategyImpl extends AbstractUploadStrategy {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SenkoConfig senkoConfig;

    /**
     * 获取上传文件的存储路径
     *
     * @return 文件存储路径
     */
    protected String getLocalPathPrefix() {
        return senkoConfig.getUpload().getLocal()
                .getPath();
    }

    /**
     * 获取文件的资源访问路径
     *
     * @param filePath 文件路径
     * @return 文件资源访问路径
     */
    protected String getUrl(String filePath) {
        return senkoConfig.getUpload().getLocal()
                .getUrl() + filePath;
    }

    /**
     * 文件是否已经存在于本地
     *
     * @param filePath 文件存储路径
     */
    @Override
    public boolean fileExists(String filePath) {
        return new File(getLocalPathPrefix() + filePath)
                .exists();
    }

    @Override
    public void uploadFile(String filePath, String fileName, InputStream inputStream) throws IOException {
        // 检查父目录是否存在
        File dir = new File(getLocalPathPrefix() + filePath);
        if (!dir.exists()) {
            // 如果目录不存在则创建
            if (!dir.mkdirs()) {
                throw new ServiceException("创建目录失败");
            }
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File localFile = new File(getLocalPathPrefix() + filePath + fileName);
            // 输入流
            bis = new BufferedInputStream(inputStream);
            // 输出流
            bos = new BufferedOutputStream(Files.newOutputStream(localFile.toPath()));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            // 刷新缓冲区
            bos.flush();
        } catch (IOException e) {
            logger.error("写入文件时失败！", e);
            throw new RuntimeException(e);
        } finally {
            StreamUtil.closeAll(inputStream, bis, bos);
        }

    }

    /**
     * 获取文件的可访问路径
     *
     * @param filePath 文件存储路径
     */
    @Override
    public String getFileURL(String filePath) {
        return getUrl(filePath);
    }
}
