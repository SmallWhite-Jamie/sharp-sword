package com.jamie.framework.shiro.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ShiroAnon
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/28 18:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ShiroAnon {
}
