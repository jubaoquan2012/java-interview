package com.interview.javabinterview.dubbo.example.anno.service;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */

import com.alibaba.dubbo.config.annotation.Service;

@Service(loadbalance = "random", timeout = 5000,weight = 3, cluster = "failsafe")
public class MyServiceANNOImpl implements MyServiceANNO {

    @Override
    public String print(String name) {
        System.out.println(name);
        return name + System.currentTimeMillis();
    }
}
