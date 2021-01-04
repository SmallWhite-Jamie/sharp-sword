package com.sharp.sword.starter.dsclock.config;

import com.sharp.sword.starter.dsclock.properties.DistributedLockProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * RedissonConfig
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/7/9 15:20
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "dcs.lock", name = "mode", havingValue = "optimize", matchIfMissing = true)
@EnableConfigurationProperties({ RedisProperties.class, DistributedLockProperties.class })
public class RedissonAutoConfiguration {

    private final Log logger = LogFactory.getLog(RedissonAutoConfiguration.class);

    /**
     * 单机模式
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.redis", name = "host")
    RedissonClient singleClient(RedisProperties redisProperties, DistributedLockProperties distributedLockProperties) {
        logger.info("Redisson client single mode creating");
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        serverConfig.setPassword(redisProperties.getPassword());
        if (distributedLockProperties.getRedisson() != null) {
            serverConfig.setConnectionPoolSize(distributedLockProperties.getRedisson().getConnectionMaximumSize());
            serverConfig.setConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getConnectionMinimumIdleSize());
            serverConfig.setSubscriptionConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getSubscriptionConnectionMinimumIdleSize());
            serverConfig.setSubscriptionConnectionPoolSize(distributedLockProperties.getRedisson().getSubscriptionConnectionMaximumSize());
            config.setThreads(distributedLockProperties.getRedisson().getThreads());
            config.setNettyThreads(distributedLockProperties.getRedisson().getNettyThreads());
        }

        return Redisson.create(config);
    }

    /**
     * 哨兵模式
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.redis", name = "sentinel.nodes")
    RedissonClient sentinelClient(RedisProperties redisProperties, DistributedLockProperties distributedLockProperties) {
        logger.info("Redisson client sentinel mode creating");
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers();
        List<String> nodes = redisProperties.getSentinel().getNodes();
        serverConfig.addSentinelAddress(nodes.stream().map(item -> "redis://" + item).toArray(String[]::new));
        serverConfig.setMasterName(redisProperties.getSentinel().getMaster());
        serverConfig.setPassword(redisProperties.getPassword());
        if (distributedLockProperties.getRedisson() != null) {
            serverConfig.setMasterConnectionPoolSize(distributedLockProperties.getRedisson().getConnectionMaximumSize());
            serverConfig.setMasterConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getConnectionMinimumIdleSize());
            serverConfig.setSlaveConnectionPoolSize(distributedLockProperties.getRedisson().getConnectionMaximumSize());
            serverConfig.setSlaveConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getConnectionMinimumIdleSize());
            serverConfig.setSubscriptionConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getSubscriptionConnectionMinimumIdleSize());
            serverConfig.setSubscriptionConnectionPoolSize(distributedLockProperties.getRedisson().getSubscriptionConnectionMaximumSize());
            config.setThreads(distributedLockProperties.getRedisson().getThreads());
            config.setNettyThreads(distributedLockProperties.getRedisson().getNettyThreads());
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.redis", name = "cluster.nodes")
    RedissonClient clusterClient(RedisProperties redisProperties, DistributedLockProperties distributedLockProperties) {
        logger.info("Redisson client cluster mode creating");
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        List<String> nodes = redisProperties.getCluster().getNodes();
        if (nodes != null && nodes.size() > 0) {
            for (String node : nodes) {
                clusterServersConfig.addNodeAddress(node);
            }
        }
        clusterServersConfig.setPassword(redisProperties.getPassword());
        if (distributedLockProperties.getRedisson() != null) {
            clusterServersConfig.setMasterConnectionPoolSize(distributedLockProperties.getRedisson().getConnectionMaximumSize());
            clusterServersConfig.setMasterConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getConnectionMinimumIdleSize());
            clusterServersConfig.setSlaveConnectionPoolSize(distributedLockProperties.getRedisson().getConnectionMaximumSize());
            clusterServersConfig.setSlaveConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getConnectionMinimumIdleSize());
            clusterServersConfig.setSubscriptionConnectionMinimumIdleSize(distributedLockProperties.getRedisson().getSubscriptionConnectionMinimumIdleSize());
            clusterServersConfig.setSubscriptionConnectionPoolSize(distributedLockProperties.getRedisson().getSubscriptionConnectionMaximumSize());
            config.setThreads(distributedLockProperties.getRedisson().getThreads());
            config.setNettyThreads(distributedLockProperties.getRedisson().getNettyThreads());
        }
        return Redisson.create(config);
    }
}
