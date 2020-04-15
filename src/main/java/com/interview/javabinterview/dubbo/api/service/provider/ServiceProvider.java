package com.interview.javabinterview.dubbo.api.service.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.interview.javabinterview.dubbo.api.service.MyServiceAPI;
import com.interview.javabinterview.dubbo.api.service.MyServiceAPIImpl;
import com.interview.javabinterview.dubbo.xml.service.MyServiceXML;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class ServiceProvider {

    public static void main(String[] args) {
        ServiceConfig<MyServiceAPI> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("java-dubbo-provider"));
        //创建注册中心,并制定Zookeeper协议、ip和端口号
        service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        //指定服务暴露的接口
        service.setInterface(MyServiceXML.class);
        //指定真实服务对象
        service.setRef(new MyServiceAPIImpl());
        //暴露服务
        service.export();
    }
}
