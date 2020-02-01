package com.jamie.framework.accesslimit.aop;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.jamie.framework.accesslimit.annotation.RateAccessLimit;
import com.jamie.framework.util.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizheng
 * @date: 20:50 2020/01/29
 * @Description: RateAccessLimitAspect
 */
@Component
@Aspect
@Slf4j
public class RateAccessLimitAspect {

    private final ConcurrentHashMap<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    @Autowired
    private HttpServletResponse response;

    @Autowired
    public HttpServletRequest request;

    @PostConstruct
    public void initMethod() {
    }
    @Pointcut("@annotation(com.jamie.framework.accesslimit.annotation.RateAccessLimit)")
    public void limit(){

    }

    @Around("limit() && @annotation(accessLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateAccessLimit accessLimit) throws Throwable {
        if (accessLimit != null && accessLimit.qps() > RateAccessLimit.NOT_LIMITED) {
            double qps = accessLimit.qps();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            if (RATE_LIMITER_CACHE.get(method.getName()) == null) {
                // 初始化 QPS
                RATE_LIMITER_CACHE.put(method.getName(), RateLimiter.create(qps));
            }
            log.debug("【{}】的QPS设置为: {}", method.getName(), RATE_LIMITER_CACHE.get(method.getName()).getRate());
            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(method.getName()) != null &&
                    !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(accessLimit.timeout(), accessLimit.timeUnit())
                    ) {
                log.debug("请求太过频繁，请稍后重试 [{}]", request.getRequestURL().toString());
                response.setContentType("application/json;charset=UTF-8");
                try(ServletOutputStream outputStream = response.getOutputStream()) {
                    outputStream.write(JSONObject.toJSONString(ApiResult.ok("请求太过频繁，请稍后重试")).getBytes("UTF-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return joinPoint.proceed();
    }

}
