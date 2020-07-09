package com.interview.javabinterview.rocketmq.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class BrokerController {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());

    public boolean initialize() {
        //boolean result = this.topicConfigManager.load();
        //result = result && this.consumerOffsetManager.load();
        //result = result && this.subscriptionGroupManager.load();
        //result = result && this.consumerFilterManager.load();

        //DefaultMessageStore.load()方法加载Commitlog文件和消费队列文件
        //result = result && this.messageStore.load();
        //this.registerProcessor();
        //然后各种定时任务....
        return true;
    }

    public void start() {
        /**
         * broker 启动时，检测 commitlog 文件与 consumequeue、index 文件中信息是否一致，
         * 如果不一致，需要根据 commitlog 文件重新恢复 consumequeue 文件和 index 文件。
         * */
        //this.messageStore.start();
        //this.remotingServer.start();
        //this.fastRemotingServer.start();
        //this.fileWatchService.start();
        //this.brokerOuterAPI.start();
        //this.pullRequestHoldService.start();
        //this.clientHousekeepingService.start();
        //this.filterServerManager.start();
        //startProcessorByHa(messageStoreConfig.getBrokerRole());
        //handleSlaveSynchronize(messageStoreConfig.getBrokerRole());

        //Broker 启动向 NameServer 注册信息
        this.registerBrokerAll(true, false, true);

        //启动定时: 发送Broker心跳包 .Broker 发送心跳包.30秒一次
        //run( BrokerController.this.registerBrokerAll(true, false, brokerConfig.isForceRegister()));
    }

    private void registerBrokerAll(boolean b, boolean b1, boolean b2) {
        //遍历NameServer 列表, Broker消息服务器依次向NameServer 发送心跳包
        List<String> nameServerList = new ArrayList<>();
        for (String namesrvAddr : nameServerList) {
            brokerOuterAPI_registerBrokerAll(namesrvAddr);
        }
    }

    private void brokerOuterAPI_registerBrokerAll(String namesrvAddr) {
        //封装请求包头 requestHeader
        Map<Object, Object> requestHeader = new HashMap<>();
        requestHeader.put("brokerAddr", "");
        requestHeader.put("brokerId", "");
        requestHeader.put("brokerName", "");
        requestHeader.put("clusterName", "");
        requestHeader.put("haServerAddr", "");//master地址,初次请求时,该值为空,slave想NameServer注册后返回

        //封装请求体: requestBody
        Map<Object, Object> requestBody = new HashMap<>();
        /**
         * 主题配置: topicConfigWrapper内部封装的是TopicConfigManager中的topicConfigTable,
         * 内部存储的是Broker启动时默认的一些topic:
         * Broker中Topic默认存储在${Rocker_Home}/store/config/topic.json中
         */
        requestBody.put("topicConfigWrapper", "");
        //消息过滤配置列表
        requestBody.put("filterServerList", "");
        registerBroker(namesrvAddr, requestHeader, requestBody);
    }

    private void registerBroker(String namesrvAddr, Map<Object, Object> requestHeader, Map<Object, Object> requestBody) {
        //创建
        //RemotingCommand request = RemotingCommand.createRequestCommand(RequestCode.REGISTER_BROKER, requestHeader);

        /**
         * 这个利用Netty 最后在NameServer中的: DefaultRequestProcessor#processRequest();
         * 2.根据RequestCode.REGISTER_BROKER: 转发到DefaultRequestProcessor#registerBroker();
         * 3.然后在
         */
        //this.remotingClient.invokeOneway(namesrvAddr, request, timeoutMills);
    }
}
