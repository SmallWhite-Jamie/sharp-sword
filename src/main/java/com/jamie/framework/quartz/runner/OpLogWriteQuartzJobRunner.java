package com.jamie.framework.quartz.runner;

import com.jamie.framework.quartz.AbstractQuartzJobClassNameKeyRunner;
import com.jamie.framework.quartz.QuartzCronProperties;
import com.jamie.framework.quartz.job.OpLogWriteQuartzJob;
import org.quartz.Job;
import org.springframework.stereotype.Component;

/**
 * @author lizheng
 * @date: 12:10 2020/02/15
 * @Description: OpLogWriteQuartzJobRunner
 */
@Component
public class OpLogWriteQuartzJobRunner extends AbstractQuartzJobClassNameKeyRunner {

    public OpLogWriteQuartzJobRunner(QuartzCronProperties properties) {
        super(properties);
    }

    @Override
    public Class<? extends Job> getJobClass() {
        return OpLogWriteQuartzJob.class;
    }

    @Override
    public String getGroup() {
        return "GROUP_LOG";
    }
}
