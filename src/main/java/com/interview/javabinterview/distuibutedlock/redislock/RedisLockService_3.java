package com.interview.javabinterview.distuibutedlock.redislock;

import redis.clients.jedis.Jedis;

/**
 * redis 分布式锁二:
 *
 * 问题:
 *  按照上面方式实现分布式锁之后，就可以轻松解决大部分问题了。网上很多博客也都是这么实现的，但是仍然有些场景是不满足的，例如一个方法获取到锁之后，
 *  可能在方法内调这个方法此时就获取不到锁了。这个时候我们就需要把锁改进成可重入式锁了。
 *    "同一个线程可重入"
 *
 * @author Ju Baoquan
 * Created at  2020/6/23
 */
public class RedisLockService_2 {

    private static final String LOCK_SUCCESS = "OK";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     *
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        if (jedis.get(lockKey).equals(requestId)) {
            System.out.println("释放锁..." + Thread.currentThread().getName() + ",identifierValue:" + requestId);
            jedis.del(lockKey);
            return true;
        }
        return false;
    }
}
