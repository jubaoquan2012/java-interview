package com.interview.javabinterview.dubbo.codeju;

import com.interview.javabinterview.dubbo.util.DubboDecodeUtil;

/**
 * Dubbo 的注册中心
 * <p>
 * 注册中心实现了分布式环境中各服务之间的注册和发现.是各个分布式节点之间的纽带
 *
 * @author Ju Baoquan
 * Created at  2020/6/2
 */
public class DubboZKDemo {

    public static void main(String[] args) {
        DubboZKDemo demo = new DubboZKDemo();
        demo.method_1();     //理论知识
        demo.method_2();     //zk作为注册中心详解
        demo.method_3();     //zk保存dubbo元数据结构
        demo.method_4();     //zk保存dubbo元数据分析
        demo.method_5();     //Dubbo注册中心通用缓存机制
        demo.method_6();     //Dubbo注册中心 registry重试机制
        demo.method_7();     //Dubbo扩展点加载机制:jdk标准和Dubbo的区别和联系
        demo.method_8();     //Dubbo扩展点加载机制: 实现原理分析
    }

    /** 注册中心 */
    public void method_1() {
        /**
         * 注册中心作用:
         *  1.动态加入
         *      一个服务提供者通过注册中心可以动态地把自己暴露给其他消费者,无需消费者煮个去更新配置文件
         *
         *  2.动态发现
         *      一个消费者可以动态感知新的配置、路由规则和新的服务提供者,无需重启服务使之生效
         *
         *  3.动态调整
         *      注册中心支持参数的动态调整,新参数自动更新到所有相关服务节点
         *
         *  4.统一配置
         *      避免了本地配置导致每个服务的配置不一致性问题
         *
         * 注册中心支持的类型:
         *  1.zookeeper  使用zk作为注册中心              "官方推荐"
         *  2.redis      使用redis作为注册中心
         *  3.default    基于Dubbo内存的默认实现         "默认方式"
         *  4.multicast  multicast模式的服务注册和发现
         */
    }

    /**
     * zk作为注册中心详解
     */
    public void method_2() {
        /**
         * 1.zk是树形数据结构:
         *      持久节点
         *      持久顺序节点
         *      临时节点
         *      临时顺序节点
         *
         *  2.Dubbo 使用zk的节点类型:
         *      只使用 持久节点 和 临时节点. 对创建的顺序没有要求
         *
         *  3.zk 注册中心采用的是"事件通知"+客户端拉取的方式
         *      1).客户端在第一次连接上注册中心时,会获取对应目录下全量的数据.并在订阅的节点上注册一个watcher,
         *         客户端与注册中心之间保持TCP长连接,后续每个节点有任何数据变化的时候,注册中心会根据watcher的
         *         回调主动通知客户端(事件通知),客户端接收到通知后,会把对应节点下的全量数据都拉取过来
         */
    }

    /**
     * zk保存dubbo元数据结构
     */
    private void method_3() {

        /**
         * 1.zk-dubbo 树形结构:
         *      zk 命令查看: ls  /dubbo/com.interview.javabinterview.dubbo.example.xml.service.ServiceXML
         *
         * +/dubbo                      //root(根节点)
         * +-- service                  //service(接口名称)
         * +------- consumers           //四种目录服务--服务消费者目录
         * +------- configurators       //四种目录服务--动态配置目录
         * +------- routers             //四种目录服务--路由配置目录
         * +------- providers           //四种目录服务--服务提供者目录
         *
         * 2.zk-dubbo 树形结构关系:
         *  (1).树的根节点是注册中心分组，下面有多个服务接口，分组值来自用户配置<dubbo:registry>中的group 属性，默认是/dubbo。
         *  (2).服务接口下包含4类子目录，分别是providers、consumers、 routers、 contigurators,
         *      这个路径是持久节点。
         *  (3).服务提供者目录(/dubbo/service/providers)下面包含的接口有多个服务者URL元数据信息。
         *  (4).服务消费者目录(/dubbo/service/consumers) 下面包含的接口有多个消费者URL元数据信息。
         *  (5).路由配置目录(/dubbo/service/routers) 下面包含多个用于消费者路由策略URL元数据信息
         *  (6).动态配置目录(/dubbo/service/configurators)下面包含多个用于服务者动态配置URL元数据信息
         */
    }

    private void method_4() {
        //zk中providers 原始的节点信息
        String providerInfo = "dubbo%3A%2F%2F10.2.1.206%3A20880%2Fcom.interview.javabinterview.dubbo.example.anno.service.MyServiceANNO%3Fanyhost%3Dtrue%26application%3Dapplication-dubbo-provider.xml%26cluster%3Dfailsafe%26dubbo%3D2.6.0%26generic%3Dfalse%26interface%3Dcom.interview.javabinterview.dubbo.example.anno.service.MyServiceANNO%26loadbalance%3Drandom%26methods%3Dprint%26pid%3D17184%26side%3Dprovider%26timeout%3D5000%26timestamp%3D1591092151773%26weight%3D3";
        String dubboInfoDecode = DubboDecodeUtil.asciiToString(providerInfo);
        System.out.println(dubboInfoDecode);

        String consuemrInfo = "consumer%3A%2F%2F10.2.1.206%2Fcom.interview.javabinterview.dubbo.example.xml.service.ServiceXML%3Fapplication%3Ddubbo-client%26category%3Dconsumers%26check%3Dfalse%26dubbo%3D2.6.0%26interface%3Dcom.interview.javabinterview.dubbo.example.xml.service.ServiceXML%26methods%3Dprint%26pid%3D19852%26side%3Dconsumer%26timestamp%3D1591090488153";
        String consuemrInfoDecode = DubboDecodeUtil.asciiToString(consuemrInfo);
        System.out.println(consuemrInfoDecode);

        /**
         * 服务提供者URL信息: (dubboInfoDecode)
         * dubbo://10.2.1.206:20880/com.interview.javabinterview.dubbo.example.anno.service.MyServiceANNO?
         *      anyhost=true
         *      &application=application-dubbo-provider.xml
         *      &cluster=failsafe
         *      &dubbo=2.6.0
         *      &generic=false
         *      &interface=com.interview.javabinterview.dubbo.example.anno.service.MyServiceANNO
         *      &loadbalance=random
         *      &methods=print
         *      &pid=17184
         *      &side=provider
         *      &timeout=5000
         *      &timestamp=1591092151773
         *      &weight=3
         *
         * 服务消费者URL信息: (consuemrInfoDecode)
         * consumer://10.2.1.206/com.interview.javabinterview.dubbo.example.xml.service.ServiceXML?
         *       application=dubbo-client
         *       &category=consumers
         *       &check=false
         *       &dubbo=2.6.0
         *       &interface=com.interview.javabinterview.dubbo.example.xml.service.ServiceXML
         *       &methods=print&pid=19852
         *       &side=consumer
         *       &timestamp=1591090488153
         */
    }

    /**
     * Dubbo注册中心通用缓存机制
     * <p>
     * 如果每次远程调用都是先从注册中心获取一次课调用的服务列表,则会让注册中心承受巨大的流量压力.
     * 所以Dubbo注册中心实现了通用的缓存机制: 抽象类: AbstractRegistry
     */
    private void method_5() {
        /**
         * 消费者或服务治理中心获取注册信息后会做本地缓存.
         *      内存中会有一份:保存在properties中
         *      磁盘中也会持久化一份: 通过file对象应用.
         *
         *  第一次启动初始化的时候会调用: AbstractRegistry#notify-->doSaveProperties() 保存 properties 和 file
         *  以后每次更新都会调用      : AbstractRegistry#notify-->doSaveProperties() 保存 properties 和 file
         *
         *  doSaveProperties() 同步或异步更新缓存
         */

    }

    /**
     * Dubbo注册中心 registry重试机制
     * <p>
     * abstract FailbackRegistry 继承 AbstractRegistry 实现重试机制
     * <p>
     * ZookeeperRegistry 和 RedisRegistry继承该抽象类(FailbackRegistry). 调用retry()方法.
     */
    private void method_6() {

    }

    /**
     * Dubbo扩展点加载机制 jdk标准和Dubbo的区别和联系
     * <p>
     * SPI机制 (Service Provider Interface)
     */
    private void method_7() {
        /**
         * 1.Java SPI 使用了策略模式,一个接口多种实现.只声明接口,具体的实现并不在程序中直接确定,
         *   而是由程序之外的配置掌控,用于具体实现的装配.
         *   (1).定义一个接口及对应的方法
         *   (2).编写改接口的一个实现类
         *   (3).在META-INF/services/目录下,创建一个以接口全路径命名的文件.如com.test.txt.spi.PrintService
         *   (4).文件内容为"具体实现类的全路径名",如果有多个,则用分行符分隔
         *   (5).在代码中通过java.util.ServiceLoader来加载具体实现类.
         *
         * 2.Dubbo SPI 在java SPI的基础上改进
         *    区别:
         *    (1).JDK标准的SPI会一次性实例化扩展点所有实现,如果有扩展实现则初始化很耗时,如果没用上也加载,则浪费资源
         *       增加了对扩展IOC和AOP的支持,一个扩展直接可以setter注入其他扩展,会一次性把接口下的
         *       所有实现类全部实例化,用户直接调用就可以
         *
         *    (2). Dubbo SPI只是加载配置文件中的类,并分成不同的种类缓存在内存中,而不会立即加载.
         *       在性能上有更好的体现.
         *
         */
    }

    /**
     * Dubbo扩展点加载机制: 实现原理分析
     */
    private void method_8() {
        //例如在目录下
    }
}
