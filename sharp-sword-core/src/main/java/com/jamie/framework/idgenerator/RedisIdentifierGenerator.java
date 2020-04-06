package com.jamie.framework.idgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * RedisIdentifierGenerator
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/3 14:34
 */
@Component("redisIdGenerator")
public final class RedisIdentifierGenerator implements IdGenerator {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    private synchronized long generateId() {
        // 当前时间秒级别作为前缀
        LocalDateTime now = LocalDateTime.now();
        long miliSecond = now.toEpochSecond(ZoneOffset.of("+8"));
        // 通过redis获取具体的ID
        RedisAtomicLong counter = new RedisAtomicLong(String.valueOf(miliSecond), Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        Long expire = counter.getExpire();
        if(expire == null || expire ==-1) {
            counter.expireAt(getExpireAtTime(now));
        }
        long seq = counter.incrementAndGet();
        return (miliSecond << 10) + seq;
    }

    private Date getExpireAtTime(LocalDateTime now) {
        LocalDateTime localDateTime = now.plusSeconds(20);
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    @Override
    public long nextId() {
        return generateId();
    }

    @Override
    public String nextIdStr() {
        return String.valueOf(generateId());
    }
}
