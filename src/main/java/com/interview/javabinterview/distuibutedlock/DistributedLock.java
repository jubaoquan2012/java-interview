package com.interview.javabinterview.edistuibuted.lock;

import org.apache.zookeeper.KeeperException;

/**
 * 分布式锁接口类
 *
 * @author Ju Baoquan
 * Created at  2020/3/27
 */
public interface DistributedLock {

    boolean lock() throws KeeperException, InterruptedException;

    void unLock() throws KeeperException, InterruptedException;

}
