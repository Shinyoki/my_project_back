package com.senko.controller.test;


import com.alibaba.fastjson.JSON;
import com.senko.common.core.vo.LogVO;
import io.swagger.annotations.ApiImplicitParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试s
 *
 * @author senko
 * @date 2022/8/26 22:12
 */
@RestController
@RequestMapping("/test")
public class SysTestController {

    private final Logger logger = LoggerFactory.getLogger(SysTestController.class);

    @GetMapping("/date")
    public String getDateInfo(LogVO logVO) {
        logger.info("得到的参数：{}", JSON.toJSONString(logVO));
        return "hello";
    }

    @PostMapping("/photo")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile")
    public String testUpload(MultipartFile file, HttpServletRequest request) {

        logger.info("得到的参数：{}", file.getOriginalFilename());
        return "hello";
    }

}
