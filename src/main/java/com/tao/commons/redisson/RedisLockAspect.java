package com.tao.commons.redisson;

import com.tao.commons.exception.BizException;
import com.tao.commons.utils.ObjectUtils;
import com.tao.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @Description: 分布式锁切片
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

    private static final String GET_LOCK_ERROR = "无法获取redis锁!";
    private static final String GET_FIELD_VALUE_ERROR = "获取锁失败";

    @Autowired
    private RedissonDistributedLocker redissonDistributedLocker;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Around(value = "@annotation(redisLock)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint, RedisLock redisLock) throws Throwable {
        boolean lockSuccess = false;
        long startTime = System.currentTimeMillis();

        // 一、使用入参替换key中大括号变量
        String lockKey = getLockKey(proceedingJoinPoint, redisLock);
        try {
            // 二、加锁
            lockSuccess = redissonDistributedLocker.tryLock(lockKey);
            if (!lockSuccess) {
                throw new BizException(GET_LOCK_ERROR);
            }
            // 三、执行业务
            return proceedingJoinPoint.proceed();
        } finally {
            // 四、释放锁
            if (checkMethodTransactional()) {
                applicationEventPublisher.publishEvent(new RedisUnlockTransactionalEventReq(lockSuccess, startTime, lockKey));
            } else {
                applicationEventPublisher.publishEvent(new RedisUnlockEventReq(lockSuccess, startTime, lockKey));
            }
        }
    }

    /**
     * 获取分布式锁key
     *
     * @param proceedingJoinPoint
     * @param redisLock
     * @return
     */
    private String getLockKey(ProceedingJoinPoint proceedingJoinPoint, RedisLock redisLock) {
        try {
            String lockKey = redisLock.key();
            Object[] args = proceedingJoinPoint.getArgs();
            List<String> params = StringUtils.getBraceContent(lockKey);
            if (args != null && args.length > 0 && params.size()> 0) {
                // 【注】只从第一个入参对象取值
                Object arg = args[0];
                for (String param : params) {
                    Object paramValue = ObjectUtils.getFieldValueByName(arg, param);
                    if (paramValue instanceof Number) {
                        lockKey = lockKey.replace("{" + param + "}", paramValue.toString());
                    } else if (paramValue instanceof Enum) {
                        lockKey = lockKey.replace("{" + param + "}", ((Enum<?>) paramValue).name());
                    } else if (paramValue instanceof String) {
                        lockKey = lockKey.replace("{" + param + "}", paramValue.toString());
                    } else {
                        log.info("RedisLockAspect.getLockKey fail. {}", paramValue);
                    }
                }
            }
            return lockKey;
        } catch (Exception e) {
            log.error(GET_FIELD_VALUE_ERROR, e);
            throw new BizException(GET_FIELD_VALUE_ERROR);
        }
    }

    /**
     * 方法是否带Transactional注解
     *
     * @return
     */
    private boolean checkMethodTransactional() {
        boolean isTransactional = false;
        try {
            TransactionAspectSupport.currentTransactionStatus();
            isTransactional = true;
        } catch (NoTransactionException e) {

        }
        return isTransactional;
    }
}