package com.jamie.framework;

import com.jamie.framework.datasource.annotation.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jamie.li
 */
@SpringBootApplication(exclude = { CacheAutoConfiguration.class }) // 禁用spring的默认缓存配置
@EnableDynamicDataSource
@ComponentScan("com.jamie.framework")
public class ApplicationRun {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(ApplicationRun.class, args);
    }
}
