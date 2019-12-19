package com.jamie.framework;

import com.jamie.framework.datasource.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@EnableDynamicDataSource
@ComponentScan("com.jamie.framework")
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }
}
