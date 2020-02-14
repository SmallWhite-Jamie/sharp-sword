package com.jamie.framework.log.sys;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author lizheng
 * @date: 17:14 2020/02/12
 * @Description: SysLogAspect
 */

@Aspect
@Component
@Slf4j
@Order(100)
public class SysLogAspect {
    @Pointcut("execution(* com.jamie.framework..controller.*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
                log.debug("url={}, method={}, class_method={}, args={}",
                        request.getRequestURL(),
                        request.getMethod(),
                        classMethod,
                        Arrays.toString(joinPoint.getArgs()));
            }
        }
        long start = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        log.info("Request time: {} Millis", System.currentTimeMillis() - start);
        return obj;
    }
}
