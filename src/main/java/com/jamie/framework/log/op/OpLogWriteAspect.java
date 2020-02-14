package com.jamie.framework.log.op;

import com.jamie.framework.redis.RedisService;
import com.jamie.framework.service.impl.AppBaseService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lizheng
 * @date: 12:20 2020/02/13
 * @Description: OpLogWriteAspect
 */
@Aspect
@Component
@Slf4j
@Order(101)
public class OpLogWriteAspect {

    private static final String REDIS_KEY = "OP_LOG_WRITE";

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppBaseService appBaseService;


    @Pointcut("@annotation(com.jamie.framework.log.op.OpLogWrite)")
    public void pointcut() {

    }

    @Before("pointcut() && @annotation(logWrite)")
    public void before(JoinPoint joinPoint, OpLogWrite logWrite) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            OpLog log = new OpLog();
            log.setClassMethod(classMethod);
            log.setClientType(logWrite.clientType());
            log.setCrtTime(new Date());
            log.setDescription(log.getDescription());
            log.setMethod(request.getMethod());
            log.setModuleName(logWrite.moduleName());
            log.setOp(logWrite.op());
            log.setUrl(request.getRequestURI());
            log.setUserId(appBaseService.getUserId());
            redisService.listRPush(REDIS_KEY, log);
        }
    }
}
