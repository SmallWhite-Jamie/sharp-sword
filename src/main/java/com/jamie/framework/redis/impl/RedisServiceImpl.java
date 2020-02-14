package com.jamie.framework.redis.impl;

import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @date: 21:13 2020/02/13
 * @Description: RedisServiceImpl
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AppProperties appProperties;

    @Override
    public void setStr(String key, String value) {
        stringRedisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value);
    }

    @Override
    public void setStr(String key, String value, long second) {
        stringRedisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value, second, TimeUnit.SECONDS);
    }

    @Override
    public void setObj(String key, String value) {
        this.set(key, value);
    }

    @Override
    public void setObj(String key, String value, long second) {
        this.set(key, value, second);
    }

    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value);
    }

    @Override
    public void set(Object key, Object value, long second) {
        redisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value, second, TimeUnit.SECONDS);
    }

    @Override
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(appProperties.getKey() + ":" + key);
    }

    @Override
    public Object getObj(String key) {
        return redisTemplate.opsForValue().get(appProperties.getKey() + ":" + key);
    }

    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(appProperties.getKey() + ":" + key);
    }

    @Override
    public boolean hasKey(Object key) {
        Boolean hasKey = redisTemplate.hasKey(appProperties.getKey() + ":" + key);
        return hasKey == null ? false : hasKey;
    }

    @Override
    public List<String> getList(String key) {
        return stringRedisTemplate.opsForList().range(appProperties.getKey() + ":" + key, 0, -1);
    }

    @Override
    public List<Object> getList(Object key) {
        return redisTemplate.opsForList().range(appProperties.getKey() + ":" + key, 0, -1);
    }

    @Override
    public void listLPush(String key, String... value) {
        stringRedisTemplate.opsForList().leftPushAll(appProperties.getKey() + ":" + key, value);
    }

    @Override
    public void listRPush(String key, String... value) {
        stringRedisTemplate.opsForList().rightPushAll(appProperties.getKey() + ":" + key, value);
    }

    @Override
    public void listLPush(Object key, Object... value) {
        redisTemplate.opsForList().leftPushAll(appProperties.getKey() + ":" + key, value);
    }

    @Override
    public void listRPush(Object key, Object... value) {
        redisTemplate.opsForList().rightPushAll(appProperties.getKey() + ":" + key, value);
    }


}
