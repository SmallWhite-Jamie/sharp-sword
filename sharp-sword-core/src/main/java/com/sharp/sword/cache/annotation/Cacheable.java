package com.sharp.sword.cache.annotation;

import com.sharp.sword.cache.enums.Medium;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法.
 * 而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。如：select方法</p>
 *
 * @author lizheng
 * @version 1.0
 * @date 15:20 2020/02/28
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {

    /**
     * 缓存的key
     * @return
     */
    String key();

    /**
     * key的前缀
     * @return
     */
    String prefix() default "";

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
