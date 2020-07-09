package com.interview.javabinterview.rocketmq.namesrv.routinfo;

import java.util.HashMap;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class BrokerData {

    /** 集群名称 */
    private String cluster;

    /** brokerName */
    private String brokerName;

    /** broker 对应的IP:Port,brokerId=0表示Master,大于0表示Slave */
    /** 主备brokerName */
    private HashMap<Long/* brokerId */, String/* broker address */> brokerAddrs;

}
