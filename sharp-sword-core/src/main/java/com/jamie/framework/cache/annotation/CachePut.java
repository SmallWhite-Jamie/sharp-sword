package com.jamie.framework.cache.annotation;

import com.jamie.framework.cache.enums.Medium;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>不会检查缓存中是否存在key,每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。如：update方法</p>
 *
 * @author lizheng
 * @version 1.0
 * @date 15:24 2020/02/28
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CachePut {

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
     * 缓存key的尝试时间，默认一直存在
     * @return
     */
    long timeout() default -1;

    boolean useSysTokenTimeout() default false;

    /**
     * 缓存介质
     * @return
     */
    Medium medium() default Medium.REDIS;

}
