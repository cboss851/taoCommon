package com.tao.commons.redisson;

import com.tao.commons.spring.SpringContextUtils;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson的分布式锁实现
 */
@Component
@RequiredArgsConstructor
public class RedissonDistributedLocker {

    // 等待获取锁，最多等待时间
    private static final long MAX_WAIT_TIME = 30;
    // 上锁后自动释放时间
    private static final long MAX_LEASE_TIME = 30;

    public RedissonClient getRedissonClient() {
        return SpringContextUtils.getBean(RedissonClient.class);
    }

    /**
     * 加锁
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 在等待获取锁的过程中休眠并禁止一切线程调度，直到获取到锁；
     *
     * @param lockKey
     * @return
     */
    public RLock lock(String lockKey) {
        RLock lock = getRedissonClient().getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 加锁，过期自动释放
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 在等待获取锁的过程中休眠并禁止一切线程调度，直到获取到锁；
     * 如果已经获取到锁，则一直会持有锁直到调用unlock方法，或者直到leaseTime的时间到了
     *
     * @param lockKey
     * @param leaseTime 自动释放锁时间
     * @return
     */
    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = getRedissonClient().getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 加锁，过期自动释放，时间单位传入
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 在等待获取锁的过程中休眠并禁止一切线程调度，直到获取到锁；
     * 如果已经获取到锁，则一直会持有锁直到调用unlock方法，或者直到leaseTime的时间到了
     *
     * @param lockKey
     * @param unit      时间单位
     * @param leaseTime 上锁后自动释放时间
     * @return
     */
    public RLock lock(String lockKey, TimeUnit unit, long leaseTime) {
        RLock lock = getRedissonClient().getLock(lockKey);
        lock.lock(leaseTime, unit);
        return lock;
    }

    /**
     * 尝试获取锁
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 该获取锁的方法不会等待，如果获取到锁则返回true，获取不到锁并直接返回false，去执行下面的；
     * 如果获取到锁，则会一直持有锁直到调用unlock方法，或者leaseTime时间到
     * 但当调用B.interrupt()会被中断等待，并抛出InterruptedException。
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放时间
     * @return
     */
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = getRedissonClient().getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 该获取锁的方法不会等待，如果获取到锁则返回true，获取不到锁并直接返回false，去执行下面的；
     * 如果获取到锁，则会一直持有锁直到调用unlock方法，或者leaseTime时间到
     * 但当调用B.interrupt()会被中断等待，并抛出InterruptedException。
     *
     * @param lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        return tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @return
     */
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, TimeUnit.SECONDS, MAX_WAIT_TIME, MAX_LEASE_TIME);
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        RLock lock = getRedissonClient().getLock(lockKey);
        lock.unlock();
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 检查此锁是否被任何线程锁定
     *
     * @param lockKey
     * @return
     */
    public boolean isLocked(String lockKey) {
        RLock lock = getRedissonClient().getLock(lockKey);
        return lock.isLocked();
    }

    /**
     * 获取锁，可被中断
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 此方式会等待，但当调用B.interrupt()会被中断等待，并抛出InterruptedException异常，否则会与lock()一样始终处于等待中，直到线程A释放锁。
     *
     * @param lockKey
     * @param leaseTime
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public RLock lockInterruptibly(String lockKey, long leaseTime, TimeUnit unit) throws InterruptedException {
        RLock lock = getRedissonClient().getLock(lockKey);
        lock.lockInterruptibly(leaseTime, unit);
        return lock;
    }

    /**
     * 获取锁，可被中断
     * <p>
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。如果此时B要去获取锁
     * 此方式会等待，但当调用B.interrupt()会被中断等待，并抛出InterruptedException异常，否则会与lock()一样始终处于等待中，直到线程A释放锁。
     *
     * @param lockKey
     * @param leaseTime
     * @return
     * @throws InterruptedException
     */
    public RLock lockInterruptibly(String lockKey, long leaseTime) throws InterruptedException {
        RLock lock = getRedissonClient().getLock(lockKey);
        lock.lockInterruptibly(leaseTime, TimeUnit.SECONDS);
        return lock;
    }
}