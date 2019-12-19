package com.jamie.framework.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-10)
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Before(value = "@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
        String dsId = targetDataSource.value();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            log.warn("数据源[{}]不存在, {}, 使用默认数据源。", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceContextHolder.DEF_KEY);
        } else {
            log.info("使用数据源[{}], {}" , dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(targetDataSource.value());
        }
    }

    @After(value = "@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        log.info("恢复数据源 [{}]", point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}