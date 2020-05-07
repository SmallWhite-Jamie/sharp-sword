package com.jamie.framework.shiro.support;

import com.jamie.framework.util.ApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ShiroAnonBeanPostProcessor
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/28 18:40
 */
@Component
public class ShiroAnonBeanPostProcessor implements MergedBeanDefinitionPostProcessor {

    @Autowired
    private ApplicationContextUtil applicationContextUtil;

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (!beanType.isAnnotationPresent(Controller.class) && !beanType.isAnnotationPresent(RestController.class)) {
            return;
        }
        String[] classUrls = getUrlFormClass(beanType);
        if (beanType.isAnnotationPresent(ShiroAnon.class)) {
            this.addFilterChain(classUrls);
        } else {
            Method[] methods = beanType.getMethods();
            List<String> methodUrlList = new ArrayList<>();
            for (Method method : methods) {
                if (method.getDeclaringClass() == Object.class) {
                    continue;
                }
                if (method.isAnnotationPresent(ShiroAnon.class)) {
                    String[] methodUrls = getUrlFormMethod(method);
                    if (classUrls.length == 0) {
                        this.addFilterChain(methodUrls);
                    } else {
                        for (String classUrl : classUrls) {
                            for (String methodUrl : methodUrls) {
                                methodUrlList.add(concatUrl(classUrl, methodUrl));
                            }
                        }
                    }
                }
            }
            this.addFilterChain(methodUrlList.toArray(new String[0]));
        }
    }

    private String concatUrl(String a, String b) {
        if (a.endsWith("/")) {
            a = a.substring(0, a.lastIndexOf("/"));
        }
        if (b.startsWith("/")) {
            b = b.substring(1);
        }
        if (b.endsWith("}")) {
            b = b.substring(0, b.indexOf("{"));
        }
        return a + "/" +b;
    }

    private void addFilterChain(String[] classUrls) {
        if (classUrls.length > 0) {
            for (String url : classUrls) {
                if (!url.startsWith("/")) {
                    url = "/" + url;
                }
                if (url.endsWith("/")) {
                    applicationContextUtil.addShiroAnonUrl(url);
                } else {
                    applicationContextUtil.addShiroAnonUrl(url + "/");
                }
            }
        }
    }

    private String[] getUrlFormClass(Class<?> beanType) {
        if (beanType.isAnnotationPresent(RequestMapping.class)) {
            return beanType.getAnnotation(RequestMapping.class).value();
        }
        if (beanType.isAnnotationPresent(GetMapping.class)) {
            return beanType.getAnnotation(GetMapping.class).value();
        }
        if (beanType.isAnnotationPresent(DeleteMapping.class)) {
            return beanType.getAnnotation(DeleteMapping.class).value();
        }
        if (beanType.isAnnotationPresent(PutMapping.class)) {
            return beanType.getAnnotation(PutMapping.class).value();
        }
        if (beanType.isAnnotationPresent(PatchMapping.class)) {
            return beanType.getAnnotation(PatchMapping.class).value();
        }
        if (beanType.isAnnotationPresent(PostMapping.class)) {
            return beanType.getAnnotation(PostMapping.class).value();
        }
        return new String[0];
    }

    private String[] getUrlFormMethod(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            return method.getAnnotation(RequestMapping.class).value();
        }
        if (method.isAnnotationPresent(GetMapping.class)) {
            return method.getAnnotation(GetMapping.class).value();
        }
        if (method.isAnnotationPresent(DeleteMapping.class)) {
            return method.getAnnotation(DeleteMapping.class).value();
        }
        if (method.isAnnotationPresent(PutMapping.class)) {
            return method.getAnnotation(PutMapping.class).value();
        }
        if (method.isAnnotationPresent(PatchMapping.class)) {
            return method.getAnnotation(PatchMapping.class).value();
        }
        if (method.isAnnotationPresent(PostMapping.class)) {
            return method.getAnnotation(PostMapping.class).value();
        }
        return new String[0];
    }
}
