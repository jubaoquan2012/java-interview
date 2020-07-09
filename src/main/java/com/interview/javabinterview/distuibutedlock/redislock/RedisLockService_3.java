package com.interview.javabinterview.distuibutedlock.redislock;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;
import java.util.UUID;

/**
 * redis 分布式锁三:
 * <p>
 * 优化: 解决在本方法内继续调用分布式锁的方法: 同一线程不可重入的问题
 * <p>
 * 解决思路:
 * 也叫做递归锁，指的是在同一线程内，外层函数获得锁之后，内层递归函数仍然可以获取到该锁。
 * 换一种说法：同一个线程再次进入同步代码时，可以使用自己已获取到的锁。可重入锁可以避免因同一线程中多次获取锁而导致死锁发生。
 * 像synchronized就是一个重入锁，它是通过moniter函数记录当前线程信息来实现的。实现可重入锁需要考虑两点：
 * 　　　获取锁：首先尝试获取锁，如果获取失败，判断这个锁是否是自己的，如果是则允许再次获取， 而且必须记录重复获取锁的次数。
 * 　　　释放锁：释放锁不能直接删除了，因为锁是可重入的，如果锁进入了多次，在内层直接删除锁， 导致外部的业务在没有锁的情况下执行，
 * 会有安全问题。因此必须获取锁时累计重入的次数，释放时则减去重入次数，如果减到0，则可以删除锁。
 * <p>
 * =============================================================================================================
 * 下面我们假设锁的key为“ lock ”，hashKey是当前线程的id：“ threadId ”，锁自动释放时间假设为20
 * 获取锁的步骤：
 * 1、判断lock是否存在 EXISTS lock
 * 2、不存在，则自己获取锁，记录重入层数为1.
 * 2、存在，说明有人获取锁了，下面判断是不是自己的锁,即判断当前线程id作为hashKey是否存在：HEXISTS lock threadId
 * 3、不存在，说明锁已经有了，且不是自己获取的，锁获取失败.
 * 3、存在，说明是自己获取的锁，重入次数+1： HINCRBY lock threadId 1 ，最后更新锁自动释放时间， EXPIRE lock 20
 * <p>
 * 释放锁的步骤：
 * 1、判断当前线程id作为hashKey是否存在： HEXISTS lock threadId
 * 2、不存在，说明锁已经失效，不用管了
 * 2、存在，说明锁还在，重入次数减1： HINCRBY lock threadId -1 ，
 * 　　3、获取新的重入次数，判断重入次数是否为0，为0说明锁全部释放，删除key： DEL lock
 *
 * @author Ju Baoquan
 * Created at  2020/6/23
 */
public class RedisLockService_3 {

    /** 有两个lua脚本:lock.lua.text和 unlock.lua.text */

    private static final StringRedisTemplate redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    private static final DefaultRedisScript<Long> LOCK_SCRIPT;

    private static final DefaultRedisScript<Object> UNLOCK_SCRIPT;

    static {
        // 加载释放锁的脚本
        LOCK_SCRIPT = new DefaultRedisScript<>();
        LOCK_SCRIPT.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock.lua")));
        LOCK_SCRIPT.setResultType(Long.class);

        // 加载释放锁的脚本
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setScriptSource(new ResourceScriptSource(new ClassPathResource("unlock.lua")));
    }

    /**
     * 获取锁
     *
     * @param lockName 锁名称
     * @param releaseTime 超时时间(单位:秒)
     * @return key 解锁标识
     */
    public static String tryLock(String lockName, String releaseTime) {
        // 存入的线程信息的前缀，防止与其它JVM中线程信息冲突
        String key = UUID.randomUUID().toString();

        // 执行脚本
        Long result = redisTemplate.execute(
                LOCK_SCRIPT,
                Collections.singletonList(lockName),
                key + Thread.currentThread().getId(), releaseTime);

        // 判断结果
        if (result != null && result.intValue() == 1) {
            return key;
        } else {
            return null;
        }
    }

    /**
     * 释放锁
     *
     * @param lockName 锁名称
     * @param key 解锁标识
     */
    public static void unlock(String lockName, String key) {
        // 执行脚本
        redisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(lockName),
                key + Thread.currentThread().getId(), null);
    }
}
