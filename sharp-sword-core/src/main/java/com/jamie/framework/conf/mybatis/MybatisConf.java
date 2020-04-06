package com.jamie.framework.conf.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.mybatis.CustomIdentifierGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.jamie.framework.**.mapper")
public class MybatisConf {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }

    /**
     * 如果不使用mybatis 内置的ID生成器，可以自定义配置，通过@Qualifier("redisIdGenerator")指定生成器
     * @param idGenerator
     * @return
     */
    @Bean
    public IdentifierGenerator identifierGenerator(@Qualifier("redisIdGenerator") IdGenerator idGenerator) {
        return new CustomIdentifierGenerator(idGenerator);
    }

}
