package com.jamie.framework.conf.shiro;

import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.jwt.JwtProperties;
import com.jamie.framework.redis.RedisService;
import com.jamie.framework.shiro.filter.JWTFilter;
import com.jamie.framework.shiro.realm.JWTRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.Map;

/**
 * @author lizheng
 * @date: 17:27 2019/10/11
 * @Description: ShiroConfig
 */
@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(JWTRealm jwtRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm
        securityManager.setRealm(jwtRealm);

        //禁用sessionStorage
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);

        // 设置session管理器
        securityManager.setSessionManager(sessionManager());

//        securityManager.setSubjectFactory();

        // 设置了SecurityManager采用使用SecurityUtils的静态方法 获取用户等
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public JWTRealm jwtRealm(RedisService redisService) {
        return new JWTRealm(redisService);
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         AppProperties appProperties,
                                                         JwtProperties jwtProperties,
                                                         RedisService redisService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager 安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 添加jwtFilter
        Map<String, Filter> stringFilterMap = factoryBean.getFilters();
        stringFilterMap.put("jwtFilter", new JWTFilter(jwtProperties, redisService));
//        stringFilterMap.put("logoutFilter", new LogoutFilter());
        // 设置无权限时跳转
        factoryBean.setUnauthorizedUrl("403");
        factoryBean.setLoginUrl("/login");
        // 设置filter key 需要连接的地址， value 拦截器名称
        Map<String, String> filterChainDefinitionMap = factoryBean.getFilterChainDefinitionMap();
        if (StringUtils.isNotBlank(appProperties.getExcludeUrls())) {
            log.info("系统配置自定义开放URL: {}", appProperties.getExcludeUrls());
            String[] urls = appProperties.getExcludeUrls().trim().split(",");
            for (String url : urls) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/getLoginSalt", "anon");
        // swagger过滤
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/configuration/security", "anon");
        filterChainDefinitionMap.put("/configuration/ui", "anon");
        filterChainDefinitionMap.put("/doc", "anon");
        // 静态资源不拦截
        filterChainDefinitionMap.put("/static/**", "anon");
        // xxl-job-admin 不拦截
        filterChainDefinitionMap.put("/xxl-job-admin/**", "anon");
//        filterChainDefinitionMap.put("/logout", "logoutFilter");
        filterChainDefinitionMap.put("/**", "jwtFilter");

        return factoryBean;
    }

    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager manager = new DefaultSessionManager();
        manager.setSessionValidationSchedulerEnabled(false);
        return manager;
    }

    // shiro权限注解springAOP配置
    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
