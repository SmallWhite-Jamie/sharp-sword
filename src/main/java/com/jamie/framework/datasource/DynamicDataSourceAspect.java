package com.jamie.framework.datasource;

import com.jamie.framework.datasource.annotation.TargetDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 添加 @Order 解决 @Transactional 使用时切换数据源失败。
 * @author jamie.li
 */
@Aspect
@Order(-10)
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Pointcut("@annotation(com.jamie.framework.datasource.annotation.TargetDataSource) || @within(com.jamie.framework.datasource.annotation.TargetDataSource)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void changeDataSource(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        TargetDataSource annotation = AnnotationUtils.findAnnotation(methodSignature.getMethod(), TargetDataSource.class);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(methodSignature.getDeclaringType(), TargetDataSource.class);
        }
        String dsId = null;
        if (annotation != null) {
            dsId = annotation.value();
        }
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            log.warn("数据源[{}]不存在, {}, 使用默认数据源。", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceContextHolder.DEF_KEY);
        } else {
            log.info("使用数据源[{}], {}" , dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }
    }

    @After("pointcut()")
    public void restoreDataSource(JoinPoint point) {
        log.info("恢复数据源 [{}]", point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}