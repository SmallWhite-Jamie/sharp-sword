package com.jamie.framework.log.sys;

import com.jamie.framework.util.api.ApiResult;
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
    @Pointcut("execution(* com.jamie.framework..controller.*.*(..)) && !execution(* com.jamie.framework.jobadmin..*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if (log.isDebugEnabled()) {
                String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
                log.debug("url={}, method={}, class_method={}, args={}",
                        request.getRequestURL(),
                        request.getMethod(),
                        classMethod,
                        Arrays.toString(joinPoint.getArgs()));
            }
            long start = System.currentTimeMillis();
            Object obj = joinPoint.proceed();
            // 计算请求耗时
            long totalTime = System.currentTimeMillis() - start;
            if (obj instanceof ApiResult) {
                ((ApiResult) obj).setTotalTime(totalTime);
            }
            log.info("url: {}, request time: {} Millis", request.getRequestURL(), totalTime);
            return obj;
        }
        return null;
    }
}
