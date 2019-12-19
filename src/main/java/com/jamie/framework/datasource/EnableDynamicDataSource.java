package com.jamie.framework.datasource;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lizheng
 * @date: 15:51 2019/11/01
 * @Description: EnableDynamicDataSource
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(DynamicDataSourceRegister.class)
public @interface EnableDynamicDataSource {
}
