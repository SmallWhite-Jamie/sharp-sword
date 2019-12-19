package com.jamie.framework.datasource;

import cn.hutool.core.util.ObjectUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author lizheng
 * @date: 15:52 2019/11/01
 * @Description: DynamicDataSourceRegister
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * 自定义数据源
     */
    private Map<Object, DataSource> targetDataSources = new HashMap<>();

    /**
     * 默认数据源
     */
    private DataSource defaultDataSource;

    private Environment environment;

    private Binder envBinder;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.envBinder = Binder.get(environment);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        initDefaultDataSource();
        initSlaveDataSources();
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        propertyValues.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
    }

    private void initSlaveDataSources() {
        for (int i = 1; ; i++) {
            String name = "slave" + i;
            Map map;
            try {
                map = envBinder.bind("spring.datasource.dynamic." + name, Map.class).get();
            } catch (NoSuchElementException e) {
                break;
            }
            HikariDataSource hikariDataSource = buildHikariConfig(map);
            this.targetDataSources.put(name, hikariDataSource);
            DynamicDataSourceContextHolder.addDataSourceIds(name);
            log.info("动态数据源，[{}]数据源初始化成功{}", name, map);
        }
    }

    private void initDefaultDataSource() {
        Map map = envBinder.bind("spring.datasource.dynamic.master", Map.class).get();
        this.defaultDataSource =  buildHikariConfig(map);
        DynamicDataSourceContextHolder.addDataSourceIds(DynamicDataSourceContextHolder.DEF_KEY);
        log.info("动态数据源，主数据源初始化成功{}", map);
    }

    private HikariDataSource buildHikariConfig(Map map) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(ObjectUtil.toString(map.get("username")));
        hikariConfig.setPassword(ObjectUtil.toString(map.get("password")));
        hikariConfig.setJdbcUrl(ObjectUtil.toString(map.get("url")));
        hikariConfig.setDriverClassName(ObjectUtil.toString(map.get("driver-class-name")));
        return new HikariDataSource(hikariConfig);
    }
}
