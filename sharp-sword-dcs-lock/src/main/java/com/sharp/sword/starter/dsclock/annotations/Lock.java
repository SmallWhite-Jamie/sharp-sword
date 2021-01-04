package com.sharp.sword.starter.dsclock.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * EnableDistributedLock
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/7/20 9:09
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    /**
     * 锁的 key 默认方法全路径名
     * @return
     */
    String lockKey() default "";

    /**
     * <p><b>#xxx</b> : 加'#'号前缀,会去方法入参里查找xxx字段的值,用'_'进行拼接</p>
     * <p><b>xxx</b> : 不加'#'号前缀,会直接作为key的前缀用'_'进行拼接</p>
     * @return
     */
    String lockKeyPrefix() default "";

    /**
     * 获取锁失败的提示
     * @return
     */
    String message() default "获取锁失败";

    /**
     * 尝试获取锁的等待时间，默认5s无法获取直接返回，-1 时无限的等待
     * @return
     */
    int waitTime() default 5;

    /**
     * 获取锁后的租赁时间(持有时间) 默认根据业务执行时间 自动续期设置为 -1
     * @return
     */
    int leaseTime() default -1;

    /**
     * 时间单位默认 秒级别
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
