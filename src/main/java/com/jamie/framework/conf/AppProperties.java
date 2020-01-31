package com.jamie.framework.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizheng
 * @date: 11:33 2019/10/14
 * @Description: AppProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String key;

    /**
     * 登录盐值超时时间
     */
    private int loginSaltTimeoutSeconds;

    /**
     * 不拦截的URL
     */
    private String excludeUrls;

    /**
     * rateLimit 每秒允许处理请求个数
     */
    private double rateLimitPermitsCount = 5D;

    /**
     * rateLimit 超时时间
     */
    private long rateLimitWaitTimeout = 5;
}
