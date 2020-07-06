package com.interview.javabinterview.edistuibuted.lock.redis;

import java.util.UUID;

/**
 * redis 分布式锁
 *
 * get和getSet
 *
 * @author Ju Baoquan
 * Created at  2020/3/16
 */
public class RedisLocks {
    static final String LOCK_PREFIX = "Lock_PREFIX";

    static final long LOCK_EXPIRE = 5L;


    public static void main(String[] args) {
        RedisLocks redisLock = new RedisLocks();
        try {
            redisLock.lock(LOCK_PREFIX);
            //todo
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redisLock.unLock(LOCK_PREFIX);
        }
    }



    public boolean lock(String key) {
        String lock = LOCK_PREFIX + key;
        int i = RedisDTemplate.setEX(lock, 3L, UUID.randomUUID().toString());
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void unLock(String key) {
       //lua脚本
        /**
         *  if redis.call('get',KEY[1]==ARGV[1]
         *  then
         *       return redis.call('del',KEY[1]);
         *  else
         *       return 0;
         *  end;
         */

    }

    public static class RedisDTemplate {

        /**
         * 模拟setEX
         * <p>
         * 指定的 key 并设置超时时间
         */
        public static int setEX(String key, long expireTime,String value) {
            return 1;
        }

        public static String get(String key) {
            return null;
        }

        /**
         * 模拟 getset 方法:设置指定 key 的值，并返回 key 的旧值。
         *
         * @param key key
         * @param value value
         * @return 返回给定 key 的旧值。 当 key 没有旧值时，即 key 不存在时，返回 nil
         */
        public static String getSet(String key, String value) {

            return null;
        }

        public static void del(String key) {

        }
    }
}
