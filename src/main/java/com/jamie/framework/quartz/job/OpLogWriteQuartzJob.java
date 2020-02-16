package com.jamie.framework.quartz.job;

import com.jamie.framework.log.mapper.OpLogMapper;
import com.jamie.framework.log.op.OpLog;
import com.jamie.framework.log.op.OpLogWriteAspect;
import com.jamie.framework.redis.RedisService;
import com.jamie.framework.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jamie.li
 */
@Slf4j
public class OpLogWriteQuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        RedisService redisService = ApplicationContextUtil.getBean(RedisService.class);
        OpLogMapper mapper = ApplicationContextUtil.getBean(OpLogMapper.class);
        List<Object> list = redisService.getList(OpLogWriteAspect.REDIS_KEY);
        if (CollectionUtils.isNotEmpty(list)) {
            List<OpLog> opLogs = list.stream().map(item -> (OpLog) item).collect(Collectors.toList());
            mapper.batchInsert(opLogs);
            redisService.delKey(OpLogWriteAspect.REDIS_KEY);
            log.debug("日志同步成功：{} 条", opLogs.size());
        }
    }
}