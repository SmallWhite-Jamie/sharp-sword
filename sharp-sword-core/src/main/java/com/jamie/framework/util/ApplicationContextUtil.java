package com.jamie.framework.util;

import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.service.impl.AppBaseService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

/**
 * @author lizheng
 * @date: 16:58 2019/10/17
 * @Description: ApplicationContextUtil
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

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
        if (applicationContext == null) {
            throw new ApplicationContextException("ApplicationContext 未初始化");
        }
        return ApplicationContextUtil.getBean(AppProperties.class);
    }

    public static AppBaseService getAppBaseService() {
        if (applicationContext == null) {
            throw new ApplicationContextException("ApplicationContext 未初始化");
        }
        return ApplicationContextUtil.getBean(AppBaseService.class);
    }

    public static IdGenerator getIdGenerator() {
        if (applicationContext == null) {
            throw new ApplicationContextException("ApplicationContext 未初始化");
        }
        return ApplicationContextUtil.getBean(IdGenerator.class);
    }

}
