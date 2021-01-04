package com.sharp.sword.starter.dsclock;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * AopUtil
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/7/15 8:26
 */
public class AopUtil {

    public static <T> T getTargetMethodParamValue(JoinPoint joinPoint, String paramName, Class<T> type) {
        Object[] values = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (paramName.equals(parameterNames[i]) && values[i] != null) {
                return (T) values[i];
            }
        }

        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == String.class ||
                    parameterTypes[i] == Integer.class ||
                    parameterTypes[i] == Long.class ||
                    parameterTypes[i] == Double.class ||
                    parameterTypes[i] == Float.class ||
                    parameterTypes[i] == Character.class ||
                    parameterTypes[i] == Boolean.class ||
                    parameterTypes[i] == Short.class ||
                    parameterTypes[i] == Byte.class ||
                    parameterTypes[i] == Collection.class ||
                    parameterTypes[i] == Map.class
            ) {
                continue;
            }

            List<Field> fieldList = new ArrayList<>() ;
            Class<?> tempClass = parameterTypes[i];
            while (tempClass != null) {
                fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
                tempClass = tempClass.getSuperclass();
            }
            for (Field declaredField : fieldList) {
                if (paramName.equals(declaredField.getName())) {
                    declaredField.setAccessible(true);
                    try {
                        return (T) declaredField.get(values[i]);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
