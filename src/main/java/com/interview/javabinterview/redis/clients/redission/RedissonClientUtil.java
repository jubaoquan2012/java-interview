package com.interview.javabinterview.redis.clients.redission;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class RedissonClientUtil {

    private static Logger logger = LoggerFactory.getLogger(RedissonClientUtil.class);

    private static RedissonClientUtil redissonClientUtil;

    private static RedissonClient redissonClient;

    public RedissonClientUtil() {}

    public static RedissonClientUtil getInstance() {
        if (redissonClientUtil == null) {
            synchronized (RedissonClientUtil.class) {
                if (redissonClientUtil == null) {
                    redissonClientUtil = new RedissonClientUtil();
                }
            }
        }
        return redissonClientUtil;
    }


    /**
     * 使用config 创建 Redisson
     * Redisson 适用于连接redis Server 的基础类
     *
     * @param config 配置
     * @return RedissonClient
     */
    public RedissonClient getRedisson(Config config) {
        RedissonClient redisson = Redisson.create(config);
        logger.info("成功连接 Redis Server");
        return redisson;
    }

    /**
     * 使用ip 和端口创建RedissonClient
     *
     * @param ip ip地址
     * @param port 端口号
     * @return RedissonClient
     */
    public RedissonClient getRedisson(String ip, String port) {
        Config config = new Config();
        config.useSingleServer().setAddress(ip + ":" + port);
        return getRedisson(config);
    }

    public void closeRedisson(RedissonClient redisson) {
        redisson.shutdown();
        logger.info("成功关闭 Redis Client 连接");
    }

    /**
     * 获取字符串对象
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <V> RBucket<V> getRBucket(RedissonClient redisson, String key) {
        RBucket<V> bucket = redisson.getBucket(key);
        return bucket;
    }

    /**
     * 获取map对象
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <K, V> RMap<K, V> getRMap(RedissonClient redisson, String key) {
        RMap<K, V> map = redisson.getMap(key);
        return map;
    }

    /**
     * 获取有序集合
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <V> RSortedSet<V> getRSortedSet(RedissonClient redisson, String key) {
        RSortedSet<V> sortedSet = redisson.getSortedSet(key);
        return sortedSet;
    }

    /**
     * 获取集合
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <V> RSet<V> getRSet(RedissonClient redisson, String key) {
        RSet<V> rSet = redisson.getSet(key);
        return rSet;
    }

    /**
     * 获取列表
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <V> RList<V> getRList(RedissonClient redisson, String key) {
        RList<V> rList = redisson.getList(key);
        return rList;
    }

    /**
     * 获取队列
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <V> RQueue<V> getRQueue(RedissonClient redisson, String key) {
        RQueue<V> rQueue = redisson.getQueue(key);
        return rQueue;
    }

    /**
     * 获取双端队列
     *
     * @param redisson 客户端
     * @param key key值
     * @return 结果
     */
    public <V> RDeque<V> getRDeque(RedissonClient redisson, String key) {
        RDeque<V> rDeque = redisson.getDeque(key);
        return rDeque;
    }

    /**
     * 获取锁
     *
     * @param redisson 客户端
     * @param objectName key值
     * @return
     */
    public RLock getRLock(RedissonClient redisson, String objectName) {
        RLock rLock = redisson.getLock(objectName);
        return rLock;
    }

    /**
     * 获取原子数
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public RAtomicLong getRAtomicLong(RedissonClient redisson, String objectName) {
        RAtomicLong rAtomicLong = redisson.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(RedissonClient redisson, String objectName) {
        RCountDownLatch rCountDownLatch = redisson.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <M> RTopic<M> getRTopic(RedissonClient redisson, String objectName) {
        RTopic<M> rTopic = redisson.getTopic(objectName);
        return rTopic;
    }
}
