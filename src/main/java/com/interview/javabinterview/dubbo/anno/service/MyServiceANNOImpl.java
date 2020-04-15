package com.interview.javabinterview.dubbo.anno.service;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class MyServiceANNOImpl implements MyServiceANNO {

    @Override
    public String print(String name) {
        System.out.println(name);
        return name + System.currentTimeMillis();
    }
}
