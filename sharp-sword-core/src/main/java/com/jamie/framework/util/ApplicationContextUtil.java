package com.jamie.framework.util;

import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.service.impl.AppBaseService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lizheng
 * @date: 16:58 2019/10/17
 * @Description: ApplicationContextUtil
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static final Set<String> SHIRO_ANON_URL_SET = new HashSet<>();

    public synchronized void addShiroAnonUrl(String url) {
        SHIRO_ANON_URL_SET.add(url);
    }

    public static Set<String> getShiroAnonUrlSet() {
        return SHIRO_ANON_URL_SET;
    }


    public static <T> T getBean(Class<T>  cls) {
        return applicationContext.getBean(cls);
    }

    public static Object getBean(String  name) {
        return applicationContext.getBean(name);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static AppProperties getAppProperties() {
        testApplicationContext();
        return ApplicationContextUtil.getBean(AppProperties.class);
    }

    public static AppBaseService getAppBaseService() {
        testApplicationContext();
        return ApplicationContextUtil.getBean(AppBaseService.class);
    }

    private static void testApplicationContext() {
        if (applicationContext == null) {
            throw new ApplicationContextException("ApplicationContext 未初始化");
        }
    }

    public static IdGenerator getIdGenerator() {
        testApplicationContext();
        return ApplicationContextUtil.getBean(IdGenerator.class);
    }

    public static void publishEvent(ApplicationEvent event) {
        testApplicationContext();
        applicationContext.publishEvent(event);
    }

}
