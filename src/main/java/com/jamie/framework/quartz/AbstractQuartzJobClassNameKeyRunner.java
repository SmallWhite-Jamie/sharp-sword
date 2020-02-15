package com.jamie.framework.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;

/**
 * @author lizheng
 * @date: 16:04 2020/02/15
 * @Description: AbstractQuartzJobClassNameKeyRunner
 */
public abstract class AbstractQuartzJobClassNameKeyRunner implements QuartzJobRunner {

    private QuartzCronProperties properties;

    public AbstractQuartzJobClassNameKeyRunner(QuartzCronProperties properties) {
        this.properties = properties;
    }

    @Override
    public void run(QuartzManager quartzManager) {
        Class<? extends Job> cla = getJobClass();
        if (cla != null) {
            String group = getGroup();
            String jobName = getJobName();
            String key = cla.getName().replace(".", "_");
            String cron = properties.getCronMap().get(key);
            if (StringUtils.isNotBlank(cron)) {
                quartzManager.addJob(cla, jobName, group, cron);
            } else {
                QuartzCronProperties.Detail detail = properties.getDetails().get(key);
                if (detail != null) {
                    quartzManager.addJob(cla, jobName, group, detail.getInterval(), detail.getRepeatCount());
                }
            }
        }
    }

    /**
     * 默认使用Class对象的SimpleName，子类可以覆盖
     * @return String
     */
    public String getJobName() {
        return getJobClass().getSimpleName();
    }

    /**
     * 设置 job Class
     * @return Class<? extends Job>
     */
    public abstract Class<? extends Job> getJobClass();

    /**
     * 设置 group name
     * @return String
     */
    public abstract String getGroup();
}
