## sharp-sword 是一套基于spring boot常用开发组件的后台快速开发框架

### 特性和功能：

#### 框架级别
- 基于springboot 易于使用，快速，后期扩展容易
- 模块化开发、发布、测试 **sharp-sword-run** 项目入口，**sharp-sword-core** 核心模块，可以新建业务模块集成父工程，包名必须以**com.jamie.framework**为父包
- 集成Javaweb开发常用的第三方框架和工具，功能丰富
- 前后端分离，专注于后端服务，json api接口传输数据
- 集成mybatis数据库框架，方便操作sql
- 集成[MyBatis-Plus](https://mp.baomidou.com/) 快速操作DAO
- 使用jwt，shiro做安全认证，jwt-token传输认证凭证
- 集成redis、spring cache、ehcache缓存技术
- 使用spring的 AbstractRoutingDataSource 动态数据源实现不同业务数据库的无缝切换
- 使用 redis和google.guava 的 RateLimiter做系统限流处理，对应两种 RedisAccessLimit 和 RateAccessLimit
- 使用SnowflakeIdGenerator 雪花算法生成全局 ID
- 使用lombok注解生成Java Bean等工具
- 提供全局的文件资源上传下载以及断点续传
- 集成redis分布式锁和zookeeper分布式锁的实现
- 使用easyexcel(阿里基于poi开发)快速、简单避免OOM的java处理Excel工具
- 集成java Mail，发送简单邮件、附件邮件、静态资源邮件、html邮件、复杂邮件
- 提供全局的接口返回规范和异常处理
- 集成spring boot admin，全局实时检测项目运行情况
- 集成swagger2接口文档 项目运行后访问：http://localhost:9090/api/doc
- 集成[xxl-job](https://www.xuxueli.com/xxl-job/)任务调度平台（与内置的**Quartz定时任务**模块不冲突），客户端依赖**sharp-sword-job**，需要使用sharp-sword项目内置的xxl-job服务端，直接依赖 **sharp-sword-job-admin** 模块即可，内置服务地址 http://localhost:9090/api/xxl-job-admin/toLogin 账号：admin 密码：123456

#### 业务级别
- 提供全局的日期参数处理方式和返回json序列化方式

- 提供默认的service接口和实现类，处理数据库默认字段

- 提供随机盐值+jwt+动态刷新 登录、用户权限、用户角色 模块接口

- 定时任务提供两种使用方式 
  - 1）Quartz定时任务，参考com.jamie.framework.quartz.job.OpLogWriteQuartzJob的实现和配置方式
  
  - 2）xxl-job 实现分布式定时任务，参考https://www.xuxueli.com/xxl-job
  
    

待完善。。。

### 启动方式

**开发启动方式**

运行 **sharp-sword-run** 工程中 **ApplicationRun** 的 main 方法

**部署服务器启动方式**

1. 加载项目来依赖后，运行 mvn clean install

2. 在 sharp-sword-run\target 目录下会生成打包好的项目文件 **sharp-sword.tar.gz** 和 **sharp-sword.zip** 解压缩后运行 start.sh [start|stop|restart|status]

### 致谢 

![icon](https://dss1.bdstatic.com/6OF1bjeh1BF3odCf/it/u=2783226288,1046314317&fm=74&app=80&f=PNG?w=200&h=200)

[jetbrains](https://www.jetbrains.com/) Licenses for Open Source Development
### License
[Apache License 2.0](http://www.apache.org/licenses/)
