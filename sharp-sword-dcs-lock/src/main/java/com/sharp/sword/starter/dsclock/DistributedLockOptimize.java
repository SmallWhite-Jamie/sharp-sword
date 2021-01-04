package com.sharp.sword.starter.dsclock;

import java.util.concurrent.TimeUnit;

/**
 * RedissonDistributedLocker
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/7/17 16:20
 */
public interface DistributedLockOptimize {

    /**
     * 加锁
     * @param lockKey
     */
    void lock(String lockKey);

    void lock(String lockKey, int leaseTime);

    void lock(String lockKey, TimeUnit unit, int leaseTime);

    boolean tryLock(String lockKey, int waitTime, int leaseTime, TimeUnit unit);

    void unlock(String lockKey);

}