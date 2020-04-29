package com.jamie.framework.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BeanUtils
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/5 9:33
 */
public class BeanUtils {

    /**
     * copy 集合中每一个元素
     * @param collection 集合数据
     * @param clz 返回值类型
     * @return
     */
    public static <E, T> List<T> copyListProperties(List<E> collection, Class<T> clz) {
        if (CollectionUtils.isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream().map(item -> com.jamie.framework.util.BeanUtils.copyProperties(item, clz)).collect(Collectors.toList());
    }

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static <T> T copyProperties(Object source, Class<T> clz) {
        try {
            T instance = clz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("对象copy失败");
        }
    }

}
