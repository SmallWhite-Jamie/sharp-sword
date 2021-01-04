package com.sharp.sword.starter.dsclock;

public interface DistributedLock {

    /**
     * <p>
     *     上锁，默认的锁的时间是 10s
     * </p>
     */
    boolean lock(String lock, String val);

    /**
     * <p>
     *     上锁
     * </p>
     */
    boolean lock(String lock, String val, int second);

    /**
     * <p>
     *     释放锁
     * </p>
     */
    void unlock(String lock, String val);
}