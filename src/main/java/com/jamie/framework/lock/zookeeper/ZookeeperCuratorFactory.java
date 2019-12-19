package com.jamie.framework.lock.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lizheng
 * @date: 14:28 2019/10/31
 * @Description: ZookeeperCuratorFactory
 */
@Component
public class ZookeeperCuratorFactory {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    public synchronized CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zookeeperProperties.getBackoffRetry().getBaseSleepTimeMs(),
                zookeeperProperties.getBackoffRetry().getMaxRetries());
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(zookeeperProperties.getConnectAddr())
                .connectionTimeoutMs(zookeeperProperties.getConnectTimeout())
                .sessionTimeoutMs(zookeeperProperties.getSessionTimeout())
                .retryPolicy(retryPolicy)
                .namespace(zookeeperProperties.getNamespace()).build();
        return curatorFramework;
    }

    public void close(CuratorFramework curatorFramework) {
        CloseableUtils.closeQuietly(curatorFramework);
    }

}
