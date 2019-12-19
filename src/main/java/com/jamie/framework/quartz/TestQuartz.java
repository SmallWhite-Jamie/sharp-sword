package com.jamie.framework.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TestQuartz extends QuartzJobBean {

    @Autowired
    TestService testService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
// TODO Auto-generated method stub
        testService.service1();
    }

}