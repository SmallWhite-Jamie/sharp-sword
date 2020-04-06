package com.jamie.framework.idgenerator;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jamie.li
 */
@Component("snowflakeIdGenerator")
public final class SnowflakeIdGenerator implements IdGenerator {
    /**
     * 开始时间截 (2018-01-01)
     */
    private final long twepoch = 1514736000000L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 8L;

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 毫秒级别时间截占的位数
     */
    private final long timestampBits = 41L;

    /**
     * 生成发布方式所占的位数
     */
    private final long getMethodBits = 2L;

    /**
     * 支持的最大机器id，结果是255 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 生成序列向左移8位(8)
     */
    private final long sequenceShift = workerIdBits;

    /**
     * 时间截向左移20位(12+8)
     */
    private final long timestampShift = sequenceBits + workerIdBits;

    /**
     * 生成发布方式向左移61位(41+12+8)
     */
    private final long getMethodShift = timestampBits + sequenceBits + workerIdBits;

    /**
     * 工作机器ID(0~255)
     */
    private long workerId;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 2位生成发布方式，0代表嵌入式发布、1代表中心服务器发布模式、2代表rest发布方式、3代表保留未用
     */
    private long getMethod;

    /**
     * 成发布方式的掩码，这里为3 (0b11=0x3=3)
     */
    private long maxGetMethod = -1L ^ (-1L << getMethodBits);
    /**
     * 重入锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 构造函数
     *
     * @param getMethod 发布方式     0代表嵌入式发布、1代表中心服务器发布模式、2代表rest发布方式、3代表保留未用 (0~3)
     * @param workerId 工作ID (0~255)
     */
    public SnowflakeIdGenerator(long getMethod, long workerId) {
        if (getMethod > maxGetMethod || getMethod < 0) {
            throw new IllegalArgumentException(String.format("getMethod can't be greater than %d or less than 0", maxGetMethod));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.getMethod = getMethod;
        this.workerId = workerId;
    }



    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    @Override
    public long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate idgenerator for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            lock.lock();
            try {
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } finally {
                lock.unlock();
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return // 生成方式占用2位，左移61位
                (getMethod << getMethodShift)
                // 时间差占用41位，最多69年，左移20位
                | ((timestamp - twepoch) << timestampShift)
                // 毫秒内序列，取值范围0-4095
                | (sequence << sequenceShift)
                // 工作机器，取值范围0-255
                | workerId;
    }

    @Override
    public String nextIdStr() {
        return Long.toString(nextId());
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
