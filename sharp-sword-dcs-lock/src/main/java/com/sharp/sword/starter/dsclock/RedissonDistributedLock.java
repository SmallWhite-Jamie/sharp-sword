package com.sharp.sword.starter.dsclock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * RedissonDistributedLocker
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/7/17 16:20
 */
public class RedissonDistributedLock implements DistributedLockOptimize {

    private final Log log = LogFactory.getLog(RedissonDistributedLock.class);

    private final RedissonClient redissonClient;

    public RedissonDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    @Override
    public void lock(String lockKey) {
        log.info("lock: lockKey = " + lockKey);
        redissonClient.getLock(lockKey).lock();
    }

    @Override
    public void lock(String lockKey, int leaseTime) {
        log.info("lock: lockKey = " + lockKey + ", leaseTime= "+ leaseTime + " s");
        redissonClient.getLock(lockKey).lock(leaseTime, TimeUnit.SECONDS);
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int leaseTime) {
        log.info("lock: lockKey = " + lockKey + ", leaseTime= "+ leaseTime + " " + unit);
        redissonClient.getLock(lockKey).lock(leaseTime, unit);
    }

    @Override
    public boolean tryLock(String lockKey, int waitTime, int leaseTime, TimeUnit unit) {
        log.info("try lock: lockKey = " + lockKey + ", leaseTime= "+ leaseTime + " " + unit + ", waitTime = " + waitTime);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        log.info("unlock: lockKey = " + lockKey);
        redissonClient.getLock(lockKey).unlock();
    }

}
