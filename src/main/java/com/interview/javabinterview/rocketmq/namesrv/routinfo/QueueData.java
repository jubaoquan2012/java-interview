package com.interview.javabinterview.rocketmq.namesrv.routinfo;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class QueueData {
    /** broker的名称 */
    private String brokerName;
    /** 读队列个数 */
    private int readQueueNums;
    /** 写队列个数 */
    private int writeQueueNums;
    /** 权限操作 */
    private int perm;
    /** 同步复制还是异步复制 */
    private int topicSynFlag;
}
