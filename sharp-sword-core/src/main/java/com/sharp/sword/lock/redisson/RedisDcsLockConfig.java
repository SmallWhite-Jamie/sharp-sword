package com.sharp.sword.lock.redisson;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author lizheng
 * @date: 17:18 2019/12/09
 * @Description: RedisDcsLockConfig
 */
public class RedisDcsLockConfig {

    @Bean
    public RedisDcsLock redisDcsLock(RedisProperties redisProperties) {
        return new RedisDcsLock(redisProperties);
    }

}
