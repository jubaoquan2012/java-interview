package com.interview.javabinterview.rocketmq.namesrv;

import io.netty.util.internal.logging.InternalLogger;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/27
 */
public class NamesrvStartup {
    private static InternalLogger log;
    public static void main(String[] args) {
        main0(args);
    }

    private static void main0(String[] args) {
        //NamesrvController controller = createNamesrvController(args);
       // start(controller);
    }

    private static void createNamesrvController(String[] args) {
        //step1.首先解析配置文件:NamesrvConfig:业务参数、NettyServerConfig:网络参数
        //final NamesrvConfig namesrvConfig = new NamesrvConfig();
        //final NettyServerConfig nettyServerConfig = new NettyServerConfig();
        //nettyServerConfig.setListenPort(9876); 设置默认的监听端口

        //final NamesrvController controller = new NamesrvController(namesrvConfig, nettyServerConfig);
        // remember all configs to prevent discard
        //controller.getConfiguration().registerConfig(properties);
    }

    private static NamesrvController start(NamesrvController controller) {
        //step2.根据启动属性创建 NamesrvController 实例,并初始化实例(initialize()方法),NamesrvController 实例为 NameServer核心控制器
        boolean initResult = controller.initialize();
        if (!initResult) {
            //controller.shutdown();
            System.exit(-3);
        }
        //step3.创建JVM 钩子函数,并启动服务器,以便监听Broker、消息生产者的网络请求
        //省略: 注册一个JVM函数, 在JVM进程关闭之前, 先将线程池关闭, 及时释放资源.
        controller.start();
        return controller;
    }
}
