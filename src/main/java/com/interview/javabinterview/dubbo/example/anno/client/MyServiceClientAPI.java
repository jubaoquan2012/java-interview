package com.interview.javabinterview.dubbo.anno.client;

import com.interview.javabinterview.dubbo.anno.service.MyServiceANNO;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Component;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
@Component
public class MyServiceClientAPI {

    @Reference
    private MyServiceANNO service;

    public String print(String name){
        return service.print(name);
    }
}
