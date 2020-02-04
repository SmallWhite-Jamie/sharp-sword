## sharp-sword 是一套基于spring boot常用开发组件的后台快速开发框架

**特性和功能**：

- 基于springboot 易于使用，快速，后期扩展容易
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

待完善。。。