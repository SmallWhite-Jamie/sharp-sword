package com.sharp.sword.quartz.job;

import com.sharp.sword.log.mapper.OpLogMapper;
import com.sharp.sword.log.op.OpLog;
import com.sharp.sword.log.op.OpLogWriteAspect;
import com.sharp.sword.redis.RedisService;
import com.sharp.sword.util.ApplicationContextUtil;
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