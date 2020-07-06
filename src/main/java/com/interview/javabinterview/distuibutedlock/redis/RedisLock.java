package com.interview.javabinterview.edistuibuted.lock.redis;

import org.springframework.util.StringUtils;

/**
 * redis 分布式锁
 *
 * get和getSet
 *
 * @author Ju Baoquan
 * Created at  2020/3/16
 */
public class RedisLock {

    static final String LOCK_PREFIX = "Lock_PREFIX";

    static final long LOCK_EXPIRE = 5L;

    public boolean lock(String key) {
        String lock = LOCK_PREFIX + key;
        long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
        int i = RedisDTemplate.setNX(lock, String.valueOf(expireAt));
        if (i > 0) {
            return true;
        } else {
            String value = RedisDTemplate.get(lock);
            if (!StringUtils.isEmpty(value)) {
                long expireTime = Long.parseLong(value);
                if (expireTime < System.currentTimeMillis()) {
                    //说明锁已经过期
                    String oldValue = RedisDTemplate.getSet(lock, String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1));
                    //防止死锁
                    return Long.parseLong(oldValue) < System.currentTimeMillis();
                }
            }
            return false;
        }
    }

    public void unLock(String key) {
        String lock = LOCK_PREFIX + key;
        RedisDTemplate.del(lock);
    }

    public static class RedisDTemplate {

        /**
         * 模拟setNX(SET if Not exists)
         * <p>
         * 指定的 key 不存在时，为 key 设置指定的值
         *
         * @param key key
         * @param value value
         * @return 设置成功，返回 1 。 设置失败，返回 0 。
         */
        public static int setNX(String key, String value) {
            return 0;
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
