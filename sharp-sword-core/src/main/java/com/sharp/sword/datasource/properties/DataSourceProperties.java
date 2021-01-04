package com.sharp.sword.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 多数据源属性
 * @author jamie.li
 */
@Getter
@Setter
@ToString
public class DataSourceProperties {
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;

    /**
     * 	自动提交从池中返回的连接
     */
    private boolean autoCommit = true;
    /**
     * 等待来自池的连接的最大毫秒数
     */
    private long connectionTimeout = 30000;
    /**
     * 连接允许在池中闲置的最长时间
     */
    private long idleTimeout = 600000;
    /**
     * 池中连接最长生命周期
     */
    private long maxLifetime = 30 * 60 * 1000;
    /**
     * 如果您的驱动程序支持JDBC4，我们强烈建议您不要设置此属性
     */
    private String connectionTestQuery;
    /**
     * 池中维护的最小空闲连接数
     */
    private int minimumIdle = 10;
    /**
     * 池中最大连接数，包括闲置和使用中的连接
     */
    private int maximumPoolSize = 10;
    /**
     * 连接池的用户定义名称，主要出现在日志记录和JMX管理控制台中以识别池和池配置
     */
    private String poolName = null;

    /**
     * 从池中获取的连接是否默认处于只读模式
     */
    private boolean readOnly = false;

    /**
     * 是否在其自己的事务中隔离内部池查询，例如连接活动测试
     */
    private boolean isolateInternalQueries = false;

    /**
     * 为支持 catalog 概念的数据库设置默认 catalog
     */
    private String catalog;

    /**
     * 控制从池返回的连接的默认事务隔离级别
     */
    private String transactionIsolation;
}