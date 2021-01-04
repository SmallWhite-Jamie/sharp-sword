package com.sharp.sword.starter.dsclock.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DistributedLockProperties
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/9/23 8:23
 */
@ConfigurationProperties(prefix = "dcs.lock")
public class DistributedLockProperties {

    /**
     * mode
     */
    private LockMode mode;

    /**
     * redisson 配置
     */
    private Redisson redisson = new Redisson();

    public LockMode getMode() {
        return mode;
    }

    public void setMode(LockMode mode) {
        this.mode = mode;
    }

    public Redisson getRedisson() {
        return redisson;
    }

    public void setRedisson(Redisson redisson) {
        this.redisson = redisson;
    }

    public static class Redisson {

        /**
         * Redisson 最小空闲连接
         */
        private int connectionMinimumIdleSize = 2;

        /**
         * Redisson 最大连接
         */
        private int connectionMaximumSize = 10;

        /**
         * 发布和订阅连接的最小保持连接数
         */
        private int subscriptionConnectionMinimumIdleSize = 1;

        /**
         * 发布和订阅连接的连接池最大容量
         */
        private int subscriptionConnectionMaximumSize = 20;

        /**
         * 工作线程数
         */
        private int threads = 5;

        /**
         * netty 线程数
         */
        private int nettyThreads = 5;

        public int getThreads() {
            return threads;
        }

        public void setThreads(int threads) {
            this.threads = threads;
        }

        public int getNettyThreads() {
            return nettyThreads;
        }

        public void setNettyThreads(int nettyThreads) {
            this.nettyThreads = nettyThreads;
        }

        public int getConnectionMinimumIdleSize() {
            return connectionMinimumIdleSize;
        }

        public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
            this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        }

        public int getConnectionMaximumSize() {
            return connectionMaximumSize;
        }

        public void setConnectionMaximumSize(int connectionMaximumSize) {
            this.connectionMaximumSize = connectionMaximumSize;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionMaximumSize() {
            return subscriptionConnectionMaximumSize;
        }

        public void setSubscriptionConnectionMaximumSize(int subscriptionConnectionMaximumSize) {
            this.subscriptionConnectionMaximumSize = subscriptionConnectionMaximumSize;
        }
    }

    private enum LockMode {
        /**
         * 基础版锁自定义实现
         */
        BASE,

        /**
         * 使用 Redisson 实现比较完善的分布式锁
         */
        OPTIMIZE,
    }
}
