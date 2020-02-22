package com.jamie.framework.lock.redisson;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lizheng
 * @date: 17:12 2019/12/09
 * @Description: EnableRedisDcsLock
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisDcsLockConfig.class)
public @interface EnableRedisDcsLock {
}
