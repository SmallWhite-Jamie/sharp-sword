package com.jamie.framework.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestQuartzTrigger implements ApplicationRunner {


    private final static Logger LOGGER = LogManager.getLogger(TestQuartzTrigger.class);
    @Autowired
    private QuartzManager quartzManager;

    @Value("${jobCron}")
    private String jobCron;

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("启动测试testQuartz");
        try {
            //20秒执行一次
            // quartzManager.addOrUpdateJob(TestQuartz.class, "testJobQuartz", "testGroupQuartz", jobCron);
        } catch (Exception e) {
            LOGGER.error("启动测试testQuartz异常、异常信息:{}", e.getMessage());
            e.printStackTrace();
        }
    }

}