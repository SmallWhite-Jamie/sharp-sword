package com.sharp.sword.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TestJob
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/20 9:55
 */
@Component
@Slf4j
public class TestJob {
    @XxlJob("demoJobHandler")
    public ReturnT<String> execute(String param) {
        XxlJobLogger.log("{} hello world: {}", param, System.currentTimeMillis());
        log.info("hello world: {}", System.currentTimeMillis());
        return ReturnT.SUCCESS;
    }
}
