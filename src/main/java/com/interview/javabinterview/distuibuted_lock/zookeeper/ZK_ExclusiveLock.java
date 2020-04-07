package com.interview.javabinterview.distuibuted_lock.zookeeper;

import com.interview.javabinterview.distuibuted_lock.DistributedLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * zk 分布式锁:独占锁
 * <p>
 * [1.0 版本]
 * [步骤]
 * (1).用zookeeper中一个临时节点代表锁，比如在/exlusive_lock下创建临时子节点/exlusive_lock/lock。
 * (2).所有客户端争相创建此节点，但只有一个客户端创建成功。
 * (3).创建成功代表获取锁成功，此客户端执行业务逻辑
 * (4).未创建成功的客户端，监听/exlusive_lock变更
 * (5).获取锁的客户端执行完成后，删除/exlusive_lock/lock，表示锁被释放
 * (6).锁被释放后，其他监听/exlusive_lock变更的客户端得到通知，再次争相创建临时子节点/exlusive_lock/lock。此时相当于回到了第2步。
 * <p>
 * [1.0版本问题]
 * 1.锁的获取顺序和最初客户端争抢顺序不一致，这不是一个公平锁。每次锁获取都是当次最先抢到锁的客户端。
 * 2.羊群效应，所有没有抢到锁的客户端都会监听/exlusive_lock变更。当并发客户端很多的情况下，所有的客户端都会接到通知去争抢锁，此时就出现了羊群效应
 * <p>
 * <p>
 * <p>
 * [2.0 版本]
 * <p>
 * [思想]
 * 在2.0版本中，让每个客户端在/exlusive_lock下创建(临时有序节点)，这样每个客户端都在/exlusive_lock下有自己对应的锁节点，
 * 而序号排在最前面的节点，代表对应的客户端获取锁成功。排在后面的客户端监听自己前面一个节点，那么在他前序客户端执行完成后，
 * 他将得到通知，获得锁成功。逻辑修改如下：
 * <p>
 * [步骤]
 * <p>
 * (1).每个客户端往/exlusive_lock下创建有序临时节点/exlusive_lock/lock_。创建成功后/exlusive_lock下面会有每个客户端对应的节点，
 * 如/exlusive_lock/lock_000000001
 * (2).客户端取得/exlusive_lock下子节点，并进行排序，判断排在最前面的是否为自己。
 * (3).如果自己的锁节点在第一位，代表获取锁成功，此客户端执行业务逻辑
 * (4).如果自己的锁节点不在第一位，则监听自己前一位的锁节点。例如，自己锁节点lock_000000002，那么则监听lock_000000001.
 * (5).当前一位锁节点（lock_000000001）对应的客户端执行完成，释放了锁，将会触发监听客户端（lock_000000002）的逻辑。
 * (6).监听客户端重新执行第2步逻辑，判断自己是否获得了锁。
 *
 *
 * <p>
 * Created at  2020/3/27
 */
public class ZK_ExclusiveLock implements DistributedLock {

    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    private static final int sessionTimeout = 10000;

    //zookeeper配置信息
    private ZooKeeper zkClient;

    private static final String LOCK_ROOT_PATH = "/Locks";

    private static final String LOCK_NODE_NAME = "Lock_";

    private String lockPath;

    // 监控lockPath的前一个节点的watcher
    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            //System.out.println(event.getPath() + "前锁释放");
            synchronized (this) {
                notifyAll();
            }
        }
    };

    public ZK_ExclusiveLock() throws IOException {
        zkClient = new ZooKeeper(ZK_ADDRESS, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.Disconnected) {
                    System.out.println("失去连接");
                }
            }
        });
    }

    /**
     * 获取锁的原语实现
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void acquireLock() throws KeeperException, InterruptedException {
        //创建锁节点
        createLock();
        //尝试获取锁
        attemptLock();
    }

    /**
     * 创建锁的原语实现, 在lock节点下创建该线程的锁节点
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void createLock() throws KeeperException, InterruptedException {
        //1. 如果根节点不存在,则创建根节点
        Stat stat = zkClient.exists(LOCK_ROOT_PATH, false);
        if (stat == null) {
            String s = zkClient.create(
                    LOCK_ROOT_PATH,
                    new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT
            );
           // System.out.println("创建根节点:" + s);
        }

        //2. 创建临时有序节点(EPHEMERAL_SEQUENTIAL)
        this.lockPath = zkClient.create(
                LOCK_ROOT_PATH + "/" + LOCK_NODE_NAME,
                Thread.currentThread().getName().getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );
        //System.out.println(Thread.currentThread().getName() + "锁创建" + lockPath);
    }

    private void attemptLock() throws KeeperException, InterruptedException {
        while (true) {
            //1. 获取Lock所有子节点,按照节点序号排序
            List<String> lockPaths = zkClient.getChildren(LOCK_ROOT_PATH, false);

            Collections.sort(lockPaths);
            int index = lockPaths.indexOf(lockPath.substring(LOCK_ROOT_PATH.length() + 1));

            //如果lockPath 是序号最小的,则获取锁
            if (index == 0) {
                //System.out.println(Thread.currentThread().getName() + " 锁获得, lockPath: " + lockPath);
                return;
            } else {
                // lockPath 不是最小的就监控前一个节点
                String preLockPath = lockPaths.get(index - 1);
                Stat stat = zkClient.exists(LOCK_ROOT_PATH + "/" + preLockPath, watcher);

                // 假如前一个节点不存在了，比如说执行完毕，或者执行节点掉线，重新获取锁
                if (stat == null) {
                   break;
                } else {
                    // 阻塞当前进程，直到preLockPath释放锁，被watcher观察到，notifyAll后，重新acquireLock
                    //System.out.println(" 等待前锁释放，prelocakPath：" + preLockPath);
                    synchronized (watcher) {
                        watcher.wait();
                    }
                   break;
                }
            }
        }
    }

    public void releaseLock() throws KeeperException, InterruptedException {
        zkClient.delete(lockPath, -1);
        zkClient.close();
        //System.out.println(" 锁释放：" + lockPath);
    }

    @Override
    public boolean lock() throws KeeperException, InterruptedException {
        acquireLock();
        return false;
    }

    @Override
    public void unLock() throws KeeperException, InterruptedException {
        releaseLock();
    }
}
