package com.interview.javabinterview.rocketmq.namesrv.routinfo;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class RouteInfoManager {

    private final static long BROKER_CHANNEL_EXPIRED_TIME = 1000 * 60 * 2;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 消息队列路由信息,消息发送时根据路由表进行负载均衡
     * 主题与队列关系，记录一个主题的队列分布在哪些Broker上，
     * 每个Broker上存在该主题的队列个数
     */
    private final HashMap<String/* topic */, List<QueueData>> topicQueueTable;

    /** Broker 基础信息,包含brokerName、所属集群名称、主备Broker地址 */
    private final HashMap<String/* brokerName */, BrokerData> brokerAddrTable;

    /** Broker 集群信息,存储集群中所有Broker名称 */
    private final HashMap<String/* clusterName */, Set<String/* brokerName */>> clusterAddrTable;

    /**
     * Broker 状态信息.NmeServer 每次收到心跳包时会替换该信息:
     * brokerLiveTable，当前存活的 Broker,该信息不是实时的，
     * NameServer 每10S扫描一次所有的 broker,根据心跳包的时间得知
     * broker的状态，该机制也是导致当一个 Broker 进程假死后，消息生产
     * 者无法立即感知，可能继续向其发送消息，导致失败（非高可用）
     */
    private final HashMap<String/* brokerAddr */, BrokerLiveInfo> brokerLiveTable;

    /** Broker上的FilterServer列表,用于类模式消息过滤, */
    private final HashMap<String/* brokerAddr */, List<String>/* Filter Server */> filterServerTable;

    public RouteInfoManager(){
        this.topicQueueTable = new HashMap<String, List<QueueData>>(1024);
        this.brokerAddrTable = new HashMap<String, BrokerData>(128);
        this.clusterAddrTable = new HashMap<String, Set<String>>(32);
        this.brokerLiveTable = new HashMap<String, BrokerLiveInfo>(256);
        this.filterServerTable = new HashMap<String, List<String>>(256);
    }

    /**
     * NameServer 与 Broker 空闲时长，默认2分钟，在2分钟内 Nameserver 没有收到 Broker 的心跳包，则关闭该连接。
     */
    public void scanNotActiveBroker() {

    }

    public void registerBroker(){
        // this.lock.writeLock().lockInterruptibly(); 路由注册需要加锁,防止并发修改RouteInfoManager 中的路由表

        //首先判断Broker 所有集群是否存在,如果不存在,则创建,然后将broker名加入到集群Broker集合中

        //维护BrokerData信息
        //更新BrokerLiveInfo
        //注册Broker的过滤器Server地址列表
        //this.lock.writeLock().unlock();
    }

    class BrokerLiveInfo {

        /** 存储上次收到Broker心跳包的时间 */
        private long lastUpdateTimestamp;

       // private DataVersion dataVersion;

        private Channel channel;

        private String haServerAddr;
    }
}
