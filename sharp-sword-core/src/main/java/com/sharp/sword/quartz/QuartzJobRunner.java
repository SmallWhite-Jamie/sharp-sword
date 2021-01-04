package com.sharp.sword.quartz;

/**
 * @author lizheng
 * @date: 11:50 2020/02/15
 * @Description: QuartzJobRunner
 */
public interface QuartzJobRunner {
    /**
     * 系统初始化定时任务后会被调用
     * @param quartzManager
     */
    void run(QuartzManager quartzManager);
}
