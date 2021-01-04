package com.sharp.sword.conf;

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
     * 允许跨域的域名
     */
    private String allowedOrigin;

    /**
     * 文件路径
     */
    private String filePath;
}
