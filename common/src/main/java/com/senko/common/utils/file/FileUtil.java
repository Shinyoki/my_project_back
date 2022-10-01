package com.senko.common.utils.file;

import com.senko.common.utils.StreamUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件工具类
 *
 * @author senko
 * @date 2022/10/1 8:27
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 文件MD5
     * <a href="https://www.jianshu.com/p/8ac5f742baa5">...</a>
     * @param inputStream   文件流
     * @return MD5值
     */
    public static String digestMd5(InputStream inputStream) {
        try {
            // 提取MD5算法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                // 更新MD5
                md5.update(buffer, 0, length);
            }
            byte[] digest = md5.digest();
            // 转换为16进制字符
            return new String(Hex.encodeHex(digest));
        } catch (NoSuchAlgorithmException | IOException e) {
            logger.error("文件MD5计算失败", e);
            return "";
        } finally {
            StreamUtil.closeAll(inputStream);
        }
    }

    /**
     * 获取文件拓展名
     * @param fileName  文件名
     * @return          文件拓展名 如
     */
    public static String getFileExt(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

}
