package com.jamie.framework.log.op;

import com.jamie.framework.log.enumeration.ClientType;
import com.jamie.framework.log.enumeration.OP;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lizheng
 * @date: 12:00 2020/02/13
 * @Description: OpLogWrite
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OpLogWrite {
    /**
     * 模块名称 长度限制500
     *
     * @return String
     */
    String moduleName() default "";

    /**
     * 方法描述 长度限制2000
     *
     * @return String
     */
    String description() default "";

    /**
     * 客户端类型
     * @return ClientType
     */
    ClientType clientType() default ClientType.PC;

    /**
     * 日志类型
     *
     * @return OP
     */
    OP op() default OP.QUERY;
}
