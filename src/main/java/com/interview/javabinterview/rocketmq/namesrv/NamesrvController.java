package com.interview.javabinterview.rocketmq.namesrv;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class NamesrvController {


    public boolean initialize() {
        //加载KV配置
        //this.kvConfigManager.load();

        //创建Netty网络处理对象
        //this.remotingServer = new NettyRemotingServer(this.nettyServerConfig, this.brokerHousekeepingService);

        //this.remotingExecutor = Executors.newFixedThreadPool(nettyServerConfig.getServerWorkerThreads(), new ThreadFactoryImpl("RemotingExecutorThread_"));

        //this.registerProcessor();

        /**
         * 定时任务1: NameServer 每隔10s扫描一次Broker,移除处于不激活状态的Broker
         * run(NamesrvController.this.routeInfoManager.scanNotActiveBroker())
         *
         * 定时任务2:NameServer 每隔10分钟打印一次KV 配置
         * run(NamesrvController.this.kvConfigManager.printAllPeriodically())
         */
        return true;
    }

    public void start() {
        //this.remotingServer.start();
    }
}
