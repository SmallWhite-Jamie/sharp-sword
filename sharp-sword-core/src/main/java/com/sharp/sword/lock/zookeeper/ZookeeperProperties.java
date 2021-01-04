package com.sharp.sword.lock.zookeeper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizheng
 * @date: 11:54 2019/10/31
 * @Description: ZookeeperProperties
 */
@Component
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    private String connectAddr = "127.0.0.1:2181";
    private String namespace = "super";
    private int connectTimeout = 15000;
    private int sessionTimeout = 60000;
    private BackoffRetry backoffRetry = new BackoffRetry();

    public String getConnectAddr() {
        return connectAddr;
    }

    public void setConnectAddr(String connectAddr) {
        this.connectAddr = connectAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public BackoffRetry getBackoffRetry() {
        return backoffRetry;
    }

    public void setBackoffRetry(BackoffRetry backoffRetry) {
        this.backoffRetry = backoffRetry;
    }

    public static class BackoffRetry {
        private int baseSleepTimeMs = 1000;
        private int maxRetries = 10;

        public int getBaseSleepTimeMs() {
            return baseSleepTimeMs;
        }

        public void setBaseSleepTimeMs(int baseSleepTimeMs) {
            this.baseSleepTimeMs = baseSleepTimeMs;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }
    }


}
