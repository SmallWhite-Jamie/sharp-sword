package com.sharp.sword.starter.dsclock.config;

import com.sharp.sword.starter.dsclock.DistributedLockOptimize;
import com.sharp.sword.starter.dsclock.RedisLock;
import com.sharp.sword.starter.dsclock.RedissonDistributedLock;
import com.sharp.sword.starter.dsclock.aop.DistributedLockAop;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedissonDistributedLockAutoConfiguration
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/9/23 8:36
 */
@Configuration
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class DistributedLockAutoConfiguration {

    private final Log log = LogFactory.getLog(DistributedLockAutoConfiguration.class);

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dcs.lock", name = "mode", havingValue = "optimize", matchIfMissing = true)
    DistributedLockOptimize redissonDistributedLock(RedissonClient redissonClient) {
        log.info("dcs.lock: optimize mode, crete DistributedLockOptimize ");
        return new RedissonDistributedLock(redissonClient);
    }

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dcs.lock", name = "mode", havingValue = "optimize", matchIfMissing = true)
    DistributedLockAop distributedLockAop(DistributedLockOptimize distributedLockOptimize) {
        log.info("dcs.lock: optimize mode, crete DistributedLockAop ");
        return new DistributedLockAop(distributedLockOptimize);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "dcs.lock", name = "mode", havingValue = "base")
    @ConditionalOnBean(RedisTemplate.class)
    RedisLock redisLock(RedisTemplate<String, Object> redisTemplate) {
        log.info("dcs.lock: base mode, crete RedisLock ");
        return new RedisLock(redisTemplate);
    }

}
