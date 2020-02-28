package com.jamie.framework.cache.aop;

import com.jamie.framework.cache.annotation.CacheEvict;
import com.jamie.framework.cache.annotation.CachePut;
import com.jamie.framework.cache.annotation.Cacheable;
import com.jamie.framework.jwt.JwtProperties;
import com.jamie.framework.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Parameter;

/**
 * <p>CacheAspect</p>
 *
 * @author lizheng
 * @version 1.0
 * @date 17:04 2020/02/28
 */
@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtProperties jwtProperties;

    @Around("@annotation(cacheable)")
    public Object around(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        Object key = cacheable.key();
        if (cacheable.key().startsWith("#")) {
            // 得到参数中对应的值，作为key
            key = getKeyFromParams(cacheable.key(), joinPoint);
        }
        Assert.notNull(key, "缓存未指定key");

        // 检查redis中是否存在 key
        boolean hasKey;
        if (StringUtils.isNotBlank(cacheable.prefix())) {
            hasKey = redisService.hasKey(cacheable.prefix() + key.toString());
        } else {
            hasKey = redisService.hasKey(key);
        }

        // 如果存在key直接返回redis数据
        if (hasKey) {
           return redisService.getObj(key);
        }

        //不存在就执行业务方法
        Object obj = joinPoint.proceed();

        if (obj != null) {
            //将结果存入redis
            if (cacheable.timeout() > 0) {
                redisService.set(key, obj, cacheable.timeout());
            } else if (cacheable.useSysTokenTimeout()) {
                redisService.set(key, obj, jwtProperties.getExpireSecond());
            } else {
                redisService.set(key, obj);
            }
        }
        return obj;
    }

    private Object getKeyFromParams(String key, JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (key.equals(parameters[i].getName())) {
                    return args[i];
                }
            }
        }
        return null;
    }

    @AfterReturning("@annotation(cacheEvict)")
    public void cacheEvict(JoinPoint joinPoint, CacheEvict cacheEvict) {
        Object key = cacheEvict.key();
        if (cacheEvict.key().startsWith("#")) {
            // 得到参数中对应的值，作为key
            key = getKeyFromParams(cacheEvict.key(), joinPoint);
        }
        Assert.notNull(key, "缓存未指定key");
        if (key instanceof String) {
            redisService.delKey((String) key);
        } else {
            redisService.delKey(key);
        }

    }

    @Around("@annotation(cachePut)")
    public Object cachePut(ProceedingJoinPoint joinPoint, CachePut cachePut) throws Throwable {
        Object obj = joinPoint.proceed();
        if (obj == null) {
            return null;
        }
        // 获取key
        Object key = cachePut.key();
        if (cachePut.key().startsWith("#")) {
            // 得到参数中对应的值，作为key
            key = getKeyFromParams(cachePut.key(), joinPoint);
        }
        Assert.notNull(key, "缓存未指定key");
        //将结果存入redis
        if (cachePut.timeout() > 0) {
            redisService.set(key, obj, cachePut.timeout());
        } else if (cachePut.useSysTokenTimeout()) {
            redisService.set(key, obj, jwtProperties.getExpireSecond());
        } else {
            redisService.set(key, obj);
        }
        return obj;
    }



}
