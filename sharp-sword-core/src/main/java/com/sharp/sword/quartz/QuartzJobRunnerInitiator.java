package com.sharp.sword.quartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jamie.li
 */
@Component
@Slf4j
public class QuartzJobRunnerInitiator implements ApplicationContextAware, ApplicationRunner {

    @Autowired
    private QuartzManager quartzManager;

    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        if (log.isDebugEnabled()) {
            log.debug("开始初始化定时任务...");
        }
        List<Object> runners = new ArrayList<>(context.getBeansOfType(QuartzJobRunner.class).values());
        if (CollectionUtils.isNotEmpty(runners)) {
            AnnotationAwareOrderComparator.sort(runners);
            for (Object runner : runners) {
                ((QuartzJobRunner) runner).run(quartzManager);
                if (log.isDebugEnabled()) {
                    log.debug("初始化定时任务：{}",runner.getClass().getName());
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("完成定时任务初始化");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }
}