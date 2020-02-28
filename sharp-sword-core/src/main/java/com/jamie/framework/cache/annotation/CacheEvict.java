package com.jamie.framework.cache.annotation;

import com.jamie.framework.cache.enums.Medium;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>方法执行后会清除缓存，如：delete方法</p>
 *
 * @author lizheng
 * @version 1.0
 * @date 15:23 2020/02/28
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvict {

    /**
     * 缓存的key
     * @return
     */
    String key();

    /**
     * key的前缀
     * @return
     */
    String prefix();

    /**
     * 缓存介质
     * @return
     */
    Medium medium() default Medium.REDIS;
}
