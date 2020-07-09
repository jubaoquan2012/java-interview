package com.interview.javabinterview.dubbo.example.anno.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.interview.javabinterview.dubbo.example.anno.service.MyServiceANNO;
import org.springframework.stereotype.Component;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
@Component
public class MyServiceClientAPI {

    @Reference(loadbalance = "roundrobin", timeout = 1, cluster = "failsafe", mock = "com.interview.javabinterview.dubbo.example.anno.client.mock.MyServiceANNOMock")
    private MyServiceANNO service;

    public String print(String name){
        return service.print(name);
    }
}
