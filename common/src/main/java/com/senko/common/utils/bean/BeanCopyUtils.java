package com.senko.common.utils.bean;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 赋值同名类
 *
 * @author senko
 * @date 2022/9/11 12:28
 */
public class BeanCopyUtils {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BeanCopyUtils.class);

    public static <T> T copyObj(Object source, Class<T> targetClass) {
        T target = null;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("BeanCopyUtils.copyObj() error: {}", e.getMessage());
            e.printStackTrace();
        }
        return target;
    }

    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        List<T> targetList = null;
        try {
            targetList = sourceList.stream()
                    .map(source -> copyObj(source, targetClass)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("BeanCopyUtils.copyList() error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return targetList;
    }

}
