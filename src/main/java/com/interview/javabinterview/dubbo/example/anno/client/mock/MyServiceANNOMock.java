package com.interview.javabinterview.dubbo.example.anno.client.mock;

import com.interview.javabinterview.dubbo.example.anno.service.MyServiceANNO;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/2
 */
public class MyServiceANNOMock implements MyServiceANNO {

    @Override
    public String print(String name) {
        return "降级:" + name;
    }
}
