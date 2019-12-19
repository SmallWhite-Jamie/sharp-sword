package com.jamie.framework.lock.redisson;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @date: 16:41 2019/10/26
 * @Description: RedisDcsLock
 */
@Slf4j
public class RedisDcsLock {

    private static final String LOCK_TITLE = "redisLock_";

    private RedisProperties redisProperties;

    private Config config = new Config();

    private Redisson redisson;

    public RedisDcsLock(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        init();
    }

    private void init() {
        if (redisProperties.getCluster() == null && redisProperties.getHost() != null) {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
                singleServerConfig.setPassword(redisProperties.getPassword());
                singleServerConfig.setDatabase(redisProperties.getDatabase());
            }
        } else {
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            List<String> nodes = redisProperties.getCluster().getNodes();
            if (CollectionUtils.isNotEmpty(nodes)) {
                String[] nodeArr = new String[nodes.size()];
                for (int i = 0; i < nodes.size(); i++) {
                    nodeArr[i] = nodes.get(i);
                }
                clusterServersConfig.addNodeAddress(nodeArr);
            }
        }
        redisson = (Redisson) Redisson.create(config);
    }



    /**
     * 加锁
     * @param lockName
     * @return
     */
    public boolean acquire(String lockName) {
        //声明key对象
        String key = LOCK_TITLE + lockName;
        log.info("[{}], 开始加锁: [{}]", Thread.currentThread().getName(), key);
        RLock mylock = redisson.getLock(key);
        //加锁，并且设置锁过期时间，防止死锁的产生
        mylock.lock(2, TimeUnit.MINUTES);
        log.info("加锁成功");
        return true;
    }

    /**
     * 锁的释放
     * @param lockName
     */
    public void release(String lockName) {
        //必须是和加锁时的同一个key
        String key = LOCK_TITLE + lockName;
        //获取所对象
        RLock mylock = redisson.getLock(key);
        //释放锁（解锁）
        mylock.unlock();
        log.info("[{}], 释放锁: [{}]", Thread.currentThread().getName(), key);
    }

}
