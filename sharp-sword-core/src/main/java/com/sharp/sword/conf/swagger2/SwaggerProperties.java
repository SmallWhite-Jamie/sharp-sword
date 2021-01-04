package com.sharp.sword.conf.swagger2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizheng
 * @date: 15:34 2020/02/04
 * @Description: SwaggerProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * 标题
     */
    private String title = "sharp-sword";
    /**
     * 描述
     */
    private String description = "sharp-sword 是一套基于spring boot常用开发组件的后台快速开发框架";
    /**
     * 版本
     */
    private String version = "1.0.0";

    private String termsOfServiceUrl;

    /**
     * 联系信息
     */
    private Contact contact;

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }
}
