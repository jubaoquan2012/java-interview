package com.interview.javabinterview.dubbo.example.api.client.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.interview.javabinterview.dubbo.example.api.service.MyServiceAPI;
import com.interview.javabinterview.dubbo.example.xml.service.MyServiceXML;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class ClientConsumer {

    public static void main(String[] args) {
        ReferenceConfig<MyServiceAPI> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("java-dubbo-consumer"));
        //创建注册中心,并制定Zookeeper协议、ip和端口号
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        //指定要消费的服务接口
        reference.setInterface(MyServiceXML.class);
        //创建远程连接并做动态代理装换
        MyServiceAPI serviceAPI = reference.get();
        serviceAPI.print("jubaoquan");
    }
}
