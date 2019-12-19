package com.jamie.framework.conf;

import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.idgenerator.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lizheng
 * @date: 13:10 2019/10/13
 * @Description: AppConf
 */
@Configuration
public class AppConf {

    @Bean
    public RedisTemplate redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(0, 0);
    }
}
