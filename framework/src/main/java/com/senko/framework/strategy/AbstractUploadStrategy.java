package com.senko.framework.strategy;

import com.senko.common.exceptions.service.ServiceException;
import com.senko.common.utils.file.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 上传
 *
 * @author senko
 * @date 2022/10/1 8:23
 */
public abstract class AbstractUploadStrategy {

    /**
     * 文件是否位于目标存储位置
     * @param filePath  文件存储路径
     * @return          {@link Boolean}
     */
    protected abstract boolean fileExists(String filePath);

    /**
     * 上传文件
     * @param filePath      文件路径
     * @param fileName      文件名
     * @param inputStream   输入流
     */
    protected abstract void uploadFile(String filePath, String fileName, InputStream inputStream) throws IOException;

    /**
     * 获取最终文件的可访路径
     * @param filePath  文件存储路径
     * @return          文件可访问路径
     */
    protected abstract String getFileURL(String filePath);

    /**
     * 将流上传到指定路径
     * @param filePath      文件路径， 不要以 / 开头，而且要以 / 结尾，如 "photo/"
     * @param fileName      文件名     如"user.png"
     * @param inputStream   输入流
     * @return              url文件地址
     */
    public String doUpload(String filePath, String fileName, InputStream inputStream) {
        try {
            // 流只能读一次，再存个副本保存太吃内存了，这里直接以传入的文件名为准
            // 不再MD5，于是如果同名就会覆盖
            uploadFile(filePath, fileName, inputStream);
            return getFileURL(filePath + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将文件上传到指定路径
     * @param multipartFile 文件
     * @param filePath      存储路径,   不要以 / 开头，而且要以 / 结尾，如 "photo/"
     * @return              url文件地址
     */
    public String doUpload(String filePath, MultipartFile multipartFile) {
        try {
            // md5文件名
            String md5Hex = FileUtil.digestMd5(multipartFile.getInputStream());
            String fileName = md5Hex + FileUtil.getFileExt(multipartFile.getOriginalFilename());

            // 判断该维基是否存在
            if (fileExists(filePath + fileName)) {
                return getFileURL(filePath + fileName);
            }

            // 上传文件
            uploadFile(filePath, fileName, multipartFile.getInputStream());

            // 返回可访路径
            return getFileURL(filePath + fileName);
        } catch (IOException e) {
            throw new ServiceException("上传文件失败", e);
        }
    }

}
