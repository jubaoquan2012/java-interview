package com.interview.javabinterview.redis.clients.redission;

import com.interview.javabinterview.redis.clients.RedisClientUtil;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class RedissonClient implements RedisClientUtil {

    private static RedissonClient redissonClient;

    public RedissonClient() {}

    public static RedissonClient getInstance() {
        if (redissonClient == null) {
            synchronized (RedissonClient.class) {
                if (redissonClient == null) {
                    redissonClient = new RedissonClient();
                }
            }
        }
        return redissonClient;
    }

    public RedissonClient getRedisson(Config config){

    }
}
