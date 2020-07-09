package com.interview.javabinterview.rocketmq;

import com.interview.javabinterview.rocketmq.namesrv.routinfo.RouteInfoManager;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class RocketMQRead {

    public static void main(String[] args) {
        method_1();//NameServer理解
        method_2();//消费发送
        method_3();//消息存储
        method_4();//消息过滤
        method_5();//主从同步(HA机制)
        method_6();//事务消息
    }

    /**
     * NameServer和Broker 保持长连接.Broker状态存贮在 NameServer.RouteInfoManager#brokerLiveTable中
     * NameServer每收到一个心跳包:
     * 1.更新brokerLiveTable
     * 2.路由表(topicQueueTable,brokerAddrTable,clusterAddrTable,brokerLiveTable,filterServerTable)
     * 3.处理心跳:加读写锁:
     * (1).同一时刻,可以有多个Broker发送心跳包
     * (2).同一时刻,NameServer只处理一个Broker心跳包
     * <p>
     * <p>
     * 总结:
     * 1.NameServer收到Broker心跳包后更新brokerLiveTable中的信息,特别记录心跳时间 lastUpdateTime
     * 2.Broker每隔30秒发送心跳包到NameServer,报告自己还活着
     * 3.NameServer每隔10秒扫描brokerLiveTable,检测lastUpdateTime和当前系统时间差值是否超过120s,如果超过择移除Broker
     * 4.Producer根据Topic向NameServer getRouterByTopic
     */
    private static void method_1() {
        /**1.路由元信息 :{@link RouteInfoManager}*/

        /**
         * 2.路由注册 :
         * 通过Broker与NameServer的心跳实现的.
         * 1.Broker启动时向集群中所有的NameServer 发送心跳包(就是RouterInfo中的新消息)
         * 2.Broker 每隔30秒向集群中所有NameServer发送心跳包
         * 3.NameServer收到Broker 心跳包时会更新 brokerLiveTable缓存中 BrokerLiveInfo中的lastUpdateTimestamp
         * 4.NameServer 每隔10秒扫描 brokerLiveTable,
         *      如果连续120s没有收到心跳包(通过lastUpdateTimestamp判断),
         *      (1).NameServer将移除Broker的路由信息,
         *      (2).同时关闭Socket链接
         */

        /**
         * 3.路由删除
         * 有两个触发点触发路由删除
         * 1).NameServer 定时扫描 brokerLiveTable 检测上次心跳与当前系统的时间差,如果大于120s,则需要移除Broker以及Socket
         * 2).Broker在正常关闭的情况下,会执行 unregisterBroker指令
         */

        /**
         * 4.路由发现
         * 原则: 当Topic路由出现变化后,NameServer 不主动推送给客户端,而是由客户端定时拉取Topic最新的路由
         * 1.MQClient.Producer 发送 路由发现的RequestCode.GET_ROUTEINTO_BY_TOPIC
         * 2.NameServer 在DefaultRequestProcessor 中处理.
         * 2.1. 调用RouterInfoManager,获取 元数据信息
         * 2.2. 如果找到主题对应的路由信息并且该主题为顺序消息 NameServerConfig.orderMessageEnable=true
         *      则从NameServer.KVconfig中获取关于顺序消息相关的配置填充路由信息
         */
    }

    /**
     * 发送普通消息有三种实现方式:
     * 同步:可靠同步发
     * 发送者向MQ执行发送消息API时,同步等待,知道消息服务器返回发送结构
     * <p>
     * 异步:可靠异步发送
     * 发送者向MQ执行发送消息API时,指定消息发送成功后的回调函数,然后调用消息发送API后,立即返回,
     * 消息发送者线程不阻塞,直到运行结束,笑嘻嘻发送成功或失败的回调任务在一个新的线程中执行
     * <p>
     * 单向:单向发(OneWay)送
     * 发送者向MQ执行发送消息API时,直接返回,不等待消息服务器的结果,也不注册回调函数.
     * 简单说,就是只管发不在乎消息是否成功存储在消息服务器上.
     * <p>
     * 问题:
     * 消息队列如果进行负载
     * 消息发送如何实现高可用
     * 批量消息发送如何实现一致性
     *
     * 消息生产者启动流程:在client#DefaultMQProducerImpl.#start中处理.
     *
     *
     */
    private static void method_2() {
        /**
         *消息发送基本流程默认消息发送以同步发送,默认超时时间为3s
         * 1).消息验证
         * 2).路由查找
         * 3).发送消息(包含异常处理机制)

         */
        /**
         * 1).消息验证:
         * 消息发送之前,首先确保包生产者处于运行转态,然后验证消息是否符合相应的规范:
         *   1.主题名称,消息体不能为空,
         *   2.消息长度不能等于0
         *   3.消息长度且默认长度不能超过允许发送消息的最大长度4M(maxMessageSize = 1024*1024*4)
         */

        /**
         * 2).路由查找
         *
         * 队列选择:
         * ---1.sendLatencyFaultEnable = false 默认不启用Broker故障延迟机制
         *      TopicPublishInfo#selectOneMessageQueue(lastBrokerName)
         *          如果lastBrokerName == null : 自增取模: IncrementIndex % MessageQueueList.size 返回
         *          如果lastBrokerName != null : 自增取模 + !brokerName.equals(lastBrokerName);
         *
         * ---2.sendLatencyFaultEnable = true 启动Broker故障延迟机制,有重试次数,默认两次: 加起来1+2=3次
         *      TopicPublishInfo#selectOneMessageQueue(lastBrokerName)
         *
         *      自增取模 + isAvailable(broker是否可用判断) + reTrey(重试)
         */

        /**
         *  消息发送 过程
         *    client: 生产者调用: client.MQProducer#send {MQClientAPIImpl.sendMessage(topic,body,RequestCode.SEND_MESSAGE)}
         *    broker: 处理发送请求 SendMessageProcessor.processRequest(topic,body,RequestCode.SEND_MESSAGE)
         *    todo
         *
         * 发送消息(包含异常处理机制)
         *  同步:
         *  单向:
         *      无须等待小心吸附器返回本次消息发送结果,并且无须提供回调函数,表示消息发送根本不关心本次消息发送是否成功.
         *      实现原理和异步小心发送相同
         *  异步:
         *      消息异步发送是指消息生产者调用发送的API后，无须阻塞等待消息服务器返回本次
         *      消息发送结果，只需要提供一个回调 函数，供消息发送客户端在收到响应结果回调。异步方
         *      式相比同步方式，消息发送端的发送性能会显著提高，但为了保护消息服务器的负载压力，
         *      RocketMQ对消息发送的异步消息进行了并发控制，通过参数clientAsyncSemaphoreValue
         *      来控制，默认为65535。异步消息发送虽然也可以通过DefaultMQProducerfretryTimes-
         *      WhenSendAsyncFailed属性来控制消息重试次数，但是重试的调用人口是在收到服务端响
         *      应包时进行的，如果出现网络异常、网络超时等将不会重试。
         *
         *
         *  批量发送消息:
         *      批量消息发送是将同一主题的多条消息一起打包发送到消息服务端,减少网络调用次数,提高网络传输效率
         *
         */
    }

    /**
     *
     * 1.消息存储:
     *      CommitLog文件       :消息存储文件,所有消息主题的消息都存储在CommitLog文件中
     *      ConsumerQueue文件   :消息消费队列,消息到达CommitLog文件后,将异步转发到消息消费队列,供消息消费者消费
     *      IndexFile文件       :索引文件,主要存储Key和Offset的对应关系
     *      事务状态服务         :存储每条消息的状态
     *      定时消息服务         :每一个延迟级别对应一个消息消费队列,存储延迟队列的消息拉取进度.
     *
     *  2.实现类:store包下的:DefaultMessageStore
     *
     *  3.消息写入/读取
     *      写入:
     *        RocketMQ将所有主题的消息存储在同一个文件中,确保消息发送是"顺序写文件",尽最大的能力确保消息发送的高效性能与高吞吐量.
     *     读取:
     *       消息基于消息主题订阅机制,按消息主题检索消息带来了极大的不便,怎么解决?
     *       RocketMQ引入ConsumeQueue消息队列文件.
     *         1.每个消息主题包含多个消息消费队列,每一个消息队列有一个消息文件.
     */
    private static void method_3() {
        /**
         *  消息写入:
         *      1.先写入到MappedFile文件缓存中, 然后再从内存中flush到磁盘中持久化.
         *      2.CommitLog 文件以第一个offSet命名 例如: 00000000000000000000,00000000000009736859547(9736859547是该文件的起始offset)
         *
         *  消息消费怎么检索:
         */

    }

    private static void method_4() {

    }

    private static void method_5() {

    }

    private static void method_6() {
    }
}
