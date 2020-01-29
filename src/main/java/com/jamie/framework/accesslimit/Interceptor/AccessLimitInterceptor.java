package com.jamie.framework.accesslimit.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jamie.framework.accesslimit.annotation.AccessLimit;
import com.jamie.framework.util.ApplicationContextUtil;
import com.jamie.framework.util.api.ApiResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @date: 9:20 2020/01/29
 * @Description: AccessLimitInterceptor
 */
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimit.class)) {
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int limit = accessLimit.limit();
            int second = accessLimit.second();
            String key = request.getRemoteAddr() + "_" + request.getRequestURI();
            RedisTemplate<String, Integer> redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
            Integer currentLimit = redisTemplate.opsForValue().get(key);
            if (currentLimit == null) {
                //set时一定要加过期时间
                redisTemplate.opsForValue().set(key, 1, second, TimeUnit.SECONDS);
            } else if (currentLimit < limit) {
                redisTemplate.opsForValue().set(key, currentLimit + 1, second, TimeUnit.SECONDS);
            } else {
                response.setContentType("application/json;charset=UTF-8");
                try(ServletOutputStream outputStream = response.getOutputStream()) {
                    outputStream.write(JSONObject.toJSONString(ApiResult.ok("请求太过频繁")).getBytes("UTF-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
