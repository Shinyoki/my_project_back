package com.senko.common.utils.bean;

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

    public static <T> T copyObj(Object source, Class<T> targetClass) {
        T target = null;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return target;
    }

    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        List<T> targetList = null;
        try {
            targetList = sourceList.stream()
                    .map(source -> copyObj(source, targetClass)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return targetList;
    }

}
