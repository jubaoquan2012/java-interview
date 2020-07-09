package com.interview.javabinterview.redis;

import com.interview.javabinterview.redis.clients.redission.RedissonClientUtil;
import org.redisson.Config;
import org.redisson.RedissonClient;
import org.redisson.SingleServerConfig;
import org.redisson.core.RBucket;
import org.redisson.core.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class RedisDemo {

    public void redissonImpl() {
        //redisson 配置
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("127.0.0.1:6379");
        singleServerConfig.setPassword("redis");

        //redisson客户端
        RedissonClientUtil redissonClientUtil = RedissonClientUtil.getInstance();

        RedissonClient redissonClient = redissonClientUtil.getRedisson(config);

        RBucket<Object> key = redissonClientUtil.getRBucket(redissonClient, "key");

        while (true) {
            RLock lock = redissonClient.getLock("lock");
            try {
                lock.tryLock(10, 10, TimeUnit.SECONDS);
                System.out.println("获取锁,然后执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
