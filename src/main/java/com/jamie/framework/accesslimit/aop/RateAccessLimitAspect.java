package com.jamie.framework.accesslimit.aop;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.util.api.ApiResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @date: 20:50 2020/01/29
 * @Description: RateAccessLimitAspect
 */
@Component
@Aspect
public class RateAccessLimitAspect {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private HttpServletResponse response;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void initMethod() {
        rateLimiter = RateLimiter.create(appProperties.getRateLimitPermitsCount());
    }
    @Pointcut("@annotation(com.jamie.framework.accesslimit.annotation.RateAccessLimit)")
    public void limit(){

    }

    @Around("limit()")
    public Object around(ProceedingJoinPoint joinPoint) {
        if (rateLimiter.tryAcquire(appProperties.getRateLimitWaitTimeout(), TimeUnit.SECONDS)) {
            try {
               return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            response.setContentType("application/json;charset=UTF-8");
            try(ServletOutputStream outputStream = response.getOutputStream()) {
                outputStream.write(JSONObject.toJSONString(ApiResult.ok("请求太过频繁，请稍后重试")).getBytes("UTF-8"));
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
