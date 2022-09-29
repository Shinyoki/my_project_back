package com.senko.controller.test;


import com.alibaba.fastjson.JSON;
import com.senko.common.core.vo.OperationLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getDateInfo(OperationLogVO logVO) {
        logger.info("得到的参数：{}", JSON.toJSONString(logVO));
        return "hello";
    }
}
