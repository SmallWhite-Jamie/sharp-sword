package com.jamie.framework;

import com.jamie.framework.datasource.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jamie.li
 */
@SpringBootApplication
@EnableCaching
@EnableDynamicDataSource
@ComponentScan("com.jamie.framework")
public class ApplicationRun {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(ApplicationRun.class, args);
    }
}
