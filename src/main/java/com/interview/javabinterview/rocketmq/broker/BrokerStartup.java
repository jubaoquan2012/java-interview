package com.interview.javabinterview.rocketmq.broker;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class BrokerStartup {

    public static void main(String[] args) {
        start(createBrokerController(args));
    }

    private static BrokerController start(BrokerController brokerController) {
        brokerController.start();
        return brokerController;
    }

    private static BrokerController createBrokerController(String[] args) {
        /**
         * 创建 BrokerController 会在构造方法中设置 topicConfigManager
         * final BrokerController controller = new BrokerController(
         *                             brokerConfig,
         *                             nettyServerConfig,
         *                             nettyClientConfig,
         *                             messageStoreConfig);
         */
        BrokerController controller = new BrokerController();//这个是简写,上面带构造的是真实的
        // 初始化: 后续会调用DefaultMessageStore.load()方法加载Commitlog文件和消费队列文件
        // boolean initResult = controller.initialize();
        // 如果 initResult 为fasle :controller.shutdown();
        return controller;
    }
}
