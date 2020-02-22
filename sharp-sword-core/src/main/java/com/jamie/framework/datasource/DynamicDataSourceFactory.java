package com.jamie.framework.datasource;

import com.jamie.framework.datasource.properties.DataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author lizheng
 * @date: 20:55 2020/02/05
 * @Description: DynamicDataSourceFactory
 */
public class DynamicDataSourceFactory {

    public static HikariDataSource buildHikariDataSource(DataSourceProperties properties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(properties.getUsername());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setJdbcUrl(properties.getJdbcUrl());
        hikariConfig.setDriverClassName(properties.getDriverClassName());

        hikariConfig.setAutoCommit(properties.isAutoCommit());
        hikariConfig.setConnectionTimeout(properties.getConnectionTimeout());
        hikariConfig.setIdleTimeout(properties.getIdleTimeout());
        hikariConfig.setMaxLifetime(properties.getMaxLifetime());
        hikariConfig.setConnectionTestQuery(properties.getConnectionTestQuery());
        hikariConfig.setMinimumIdle(properties.getMinimumIdle());
        hikariConfig.setPoolName(properties.getPoolName());
        hikariConfig.setCatalog(properties.getCatalog());
        hikariConfig.setReadOnly(properties.isReadOnly());
        hikariConfig.setIsolateInternalQueries(properties.isIsolateInternalQueries());
        hikariConfig.setTransactionIsolation(properties.getTransactionIsolation());
        return new HikariDataSource(hikariConfig);
    }

}
