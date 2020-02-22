package com.jamie.framework.accesslimit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制同一IP在某个时间段内的访问次数
 * @author lizheng
 * @date: 9:17 2020/01/29
 * @Description: RedisAccessLimit
 */
@Documented
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisAccessLimit {
    /**
     * 访问次数 指定second时间段内的访问次数限制
     */
    int limit() default 10;

    /**
     * 秒 时间段
     */
    int second() default 5;
}
