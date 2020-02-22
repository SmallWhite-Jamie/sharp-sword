package com.jamie.framework.datasource;

import com.jamie.framework.datasource.properties.DataSourceProperties;
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
            DataSourceProperties properties;
            try {
                properties = envBinder.bind("spring.datasource.dynamic." + name, DataSourceProperties.class).get();
            } catch (NoSuchElementException e) {
                break;
            }
            HikariDataSource hikariDataSource = DynamicDataSourceFactory.buildHikariDataSource(properties);
            this.targetDataSources.put(name, hikariDataSource);
            DynamicDataSourceContextHolder.addDataSourceIds(name);
            log.info("动态数据源，[{}]数据源初始化成功{} ", name, properties);
        }
    }

    private void initDefaultDataSource() {
        DataSourceProperties properties = envBinder.bind("spring.datasource.dynamic.master", DataSourceProperties.class).get();
        this.defaultDataSource =  DynamicDataSourceFactory.buildHikariDataSource(properties);
        DynamicDataSourceContextHolder.addDataSourceIds(DynamicDataSourceContextHolder.DEF_KEY);
        log.info("动态数据源，主数据源初始化成功{} ", properties);
    }
}
