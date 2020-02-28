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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lizheng
 * @date: 21:13 2020/02/13
 * @Description: RedisServiceImpl
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    public RedisServiceImpl() {
        System.out.println("RedisServiceImpl");
    }

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AppProperties appProperties;

    @Override
    public void delKey(String ...keys) {
        String prefix = appProperties.getKey() + ":";
        // 此处必须Object接收 redisTemplate.delete(Collection<K> keys)
        List<Object> collect = Stream.of(keys).map(prefix::concat).collect(Collectors.toList());
        redisTemplate.delete(collect);
    }

    @Override
    public void delKey(Object ...keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public void setStr(String key, String value) {
        stringRedisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value);
    }

    @Override
    public void setStr(String key, String value, long second) {
        stringRedisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value, second, TimeUnit.SECONDS);
    }

    @Override
    public void setObj(String key, Object value) {
        this.set(key, value);
    }

    @Override
    public void setObj(String key, Object value, long second) {
        this.set(key, value, second);
    }

    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(Object key, Object value, long second) {
        if (key instanceof String) {
            redisTemplate.opsForValue().set(appProperties.getKey() + ":" + key, value, second, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(appProperties.getKey() + ":" + key);
    }

    @Override
    public Object getObj(Object key) {
        if (key instanceof String) {
            key = appProperties.getKey() + ":" + key;
        }
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public boolean hasKey(Object key) {
        Boolean hasKey;
        if (key instanceof String) {
            hasKey = redisTemplate.hasKey(appProperties.getKey() + ":" + key);
        } else {
            hasKey = redisTemplate.hasKey(key);
        }
        return hasKey == null ? false : hasKey;
    }

    @Override
    public List<String> getStrList(String key) {
        return stringRedisTemplate.opsForList().range(appProperties.getKey() + ":" + key, 0, -1);
    }

    @Override
    public List<Object> getList(Object key) {
        return redisTemplate.opsForList().range(key, 0, -1);
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
        redisTemplate.opsForList().leftPushAll(key, value);
    }

    @Override
    public void listRPush(Object key, Object... value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    @Override
    public void expireSeconds(Object key, int expireSecond) {
        this.expire(key, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public void expire(Object key, int expireSecond, TimeUnit timeUnit) {
        if (key instanceof String) {
            redisTemplate.expire(appProperties.getKey() + ":" + key, expireSecond, timeUnit);
        } else {
            redisTemplate.expire(key, expireSecond, timeUnit);
        }
    }


}
