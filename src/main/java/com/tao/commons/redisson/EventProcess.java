package com.tao.commons.redisson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
public class EventProcess {

    @Autowired
    private RedissonDistributedLocker redissonDistributedLocker;

    /**
     * 释放Redis锁
     *
     * @param eventReq
     */
    @EventListener
    public void releaseRedisLock(RedisUnlockEventReq eventReq) {
        if (eventReq.isLock()) {
            try {
                redissonDistributedLocker.unlock(eventReq.getLockKey());

                if (eventReq.getStartTime() == 0) {
                    log.info("释放锁：{}。", eventReq.getLockKey());
                } else {
                    log.info("释放锁：{}。耗时：{}ms", eventReq.getLockKey(), (System.currentTimeMillis() - eventReq.getStartTime()));
                }
            } catch (Exception e) {
                log.error("releaseRedisLock Exception", e);
            }
        }
    }

    /**
     * 事务完成后（提交或回滚），释放Redis锁
     *
     * @param eventReq
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void releaseRedisLockTransactional(RedisUnlockTransactionalEventReq eventReq) {
        if (eventReq.isLock()) {
            try {
                redissonDistributedLocker.unlock(eventReq.getLockKey());

                if (eventReq.getStartTime() == 0) {
                    log.info("释放锁【带事务】：{}。", eventReq.getLockKey());
                } else {
                    log.info("释放锁【带事务】：{}。耗时：{}ms", eventReq.getLockKey(), (System.currentTimeMillis() - eventReq.getStartTime()));
                }
            } catch (Exception e) {
                log.error("releaseRedisLockTransactional Exception", e);
            }
        }
    }
}

