package com.sharp.sword.starter.dsclock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author lizheng
 */
public class RedisLock implements DistributedLock {

    private static final int DEFAULT_SECOND_LEN = 5;

    private static final String LOCK_LUA = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return 'true' else return 'false' end";

    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) end return 'true' ";

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    private RedisScript<String> lockRedisScript;
    private RedisScript<String> unLockRedisScript;

    private RedisSerializer argsSerializer;
    private RedisSerializer resultSerializer;

    /**
     * 初始化lua 脚本
     */
    @PostConstruct
    public void init() {

        argsSerializer = new StringRedisSerializer();
        resultSerializer = new StringRedisSerializer();

        lockRedisScript = RedisScript.of(LOCK_LUA, String.class);
        unLockRedisScript = RedisScript.of(UNLOCK_LUA, String.class);
    }

    @Override
    public boolean lock(String lock, String val) {
        return this.lock(lock, val, DEFAULT_SECOND_LEN);
    }

    @Override
    public boolean lock(String lock, String val, int second) {
        List<String> keys = Collections.singletonList(lock);
        String flag = redisTemplate.execute(lockRedisScript, argsSerializer, resultSerializer, keys, val, String.valueOf(second));
        return Boolean.parseBoolean(flag);
    }

    @Override
    public void unlock(String lock, String val) {
        List<String> keys = Collections.singletonList(lock);
        redisTemplate.execute(unLockRedisScript, argsSerializer, resultSerializer, keys, val);
    }


}