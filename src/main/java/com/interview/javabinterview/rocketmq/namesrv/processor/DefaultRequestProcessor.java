package com.interview.javabinterview.rocketmq.namesrv.processor;

/**
 * RocketMQ 基于订阅发布机制:
 * 一个Topic拥有多个消息队列.
 * 一个Broker为每一个主题默认创建4个读队列4个写队列.
 * 多个Broker组成一个集群,BrokerName由相同的多台Broker组成Master-Slave架构,brokerId 为0代表Master,大于0表示Slave.
 * BrokerLiveInfo 中的lastUpdateTimestamp 存储上次收到的Broker心跳包的时间;
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class DefaultRequestProcessor {

}

