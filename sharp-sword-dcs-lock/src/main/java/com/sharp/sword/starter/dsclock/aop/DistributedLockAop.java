package com.sharp.sword.starter.dsclock.aop;

import com.sharp.sword.starter.dsclock.AopUtil;
import com.sharp.sword.starter.dsclock.DistributedLockOptimize;
import com.sharp.sword.starter.dsclock.annotations.Lock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * DistributedLockAop
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/7/20 16:10
 */
@Aspect
public class DistributedLockAop implements Ordered {

    private static final String LOCK_KEY_PREFIX_FLAG = "#";

    private final DistributedLockOptimize distributedLockOptimize;

    public DistributedLockAop(DistributedLockOptimize distributedLockOptimize) {
        this.distributedLockOptimize = distributedLockOptimize;
    }

    @Around("@annotation(lock)")
    public Object around(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        String lockKey = this.getLockKey(joinPoint, lock);
        this.lock(lock, lockKey);
        try {
            return joinPoint.proceed();
        } finally {
            distributedLockOptimize.unlock(lockKey);
        }
    }

    private void lock(Lock lock, String lockKey) {
        if (lock.waitTime() == -1) {
            distributedLockOptimize.lock(lockKey, lock.leaseTime());
        } else {
            boolean tryLock = distributedLockOptimize.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), TimeUnit.SECONDS);
            if (!tryLock) {
                throw new IllegalStateException(lock.message());
            }
        }
    }

    private String getLockKey(ProceedingJoinPoint joinPoint, Lock lock) {
        String prefix = lock.lockKeyPrefix();
        String lockKey = lock.lockKey();
        if (!StringUtils.hasText(lockKey)) {
            lockKey = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        }

        if (StringUtils.hasText(prefix)) {
            if (prefix.startsWith(LOCK_KEY_PREFIX_FLAG) && prefix.length() > 1) {
                Object targetMethodParamValue = AopUtil.getTargetMethodParamValue(joinPoint, prefix.substring(1), Object.class);
                if (targetMethodParamValue == null) {
                    return lockKey;
                }
                prefix = targetMethodParamValue.toString();
            }
            lockKey = prefix + "_" + lockKey;
        }
        return lockKey;
    }

    /**
     * 排序字段，多个切面时优先级最高，例如：@Transaction共用时，@lock注解优先级最高
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
