package com.interview.javabinterview.distuibutedlock.redislock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 分布式锁一:
 *
 * 会有以下问题:
 * 一：获取锁时
 * 　　setnx获取锁成功了，还没来得及setex服务就宕机了，由于这种非原子性的操作，死锁又发生了。其实redis提供了 nx 与 ex连用的命令。
 *
 * 　二：释放锁时
 * 　　1. 3个进程：A和B和C，在执行任务，并争抢锁，此时A获取了锁，并设置自动过期时间为10s
 * 　　2. A开始执行业务，因为某种原因，业务阻塞，耗时超过了10秒，此时锁自动释放了
 * 　　3. B恰好此时开始尝试获取锁，因为锁已经自动释放，成功获取锁
 * 　　4. A此时业务执行完毕，执行释放锁逻辑（删除key），于是B的锁被释放了，而B其实还在执行业务
 * 　　5. 此时进程C尝试获取锁，也成功了，因为A把B的锁删除了。
 * 　　  问题出现了：B和C同时获取了锁，违反了互斥性！如何解决这个问题呢？我们应该在删除锁之前，判断这个锁是否是自己设置的锁，
 *      如果不是（例如自己 的锁已经超时释放），那么就不要删除了。所以我们可以在set 锁时，存入当前线程的唯一标识！删除锁前，判断下里面的值是不是与自己标识释放一 致，如果不一致，说明不是自己的锁，就不要删除了。
 * @author Ju Baoquan
 * Created at  2020/6/23
 */
public class RedisLockService_1 {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** 获取锁之前的超时时间(获取锁的等待重试时间) */
    private long acquireTimeout = 5000;

    /** 获取锁之后的超时时间(防止死锁) */
    private int timeOut = 10000;

    @Autowired
    JedisPool jedisPool;

    /**
     * 获取分布式锁
     *
     * @return 锁标识
     */
    public boolean getRedisLock(String lockName, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 1.计算获取锁的时间
            Long endTime = System.currentTimeMillis() + acquireTimeout;
            // 2.尝试获取锁
            while (System.currentTimeMillis() < endTime) {
                // 3. 获取锁成功就设置过期时间
                if (jedis.setnx(lockName, val) == 1) {
                    jedis.expire(lockName, timeOut / 1000);
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param lockName 锁名称
     */
    public void unRedisLock(String lockName) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 释放锁
            jedis.del(lockName);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }

    // ===============================================
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
            log.info(value);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 关闭连接
     */
    public void returnResource(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
        }
    }
}
