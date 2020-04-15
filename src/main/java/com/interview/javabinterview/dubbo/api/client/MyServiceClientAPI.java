package com.interview.javabinterview.dubbo.api.client;

import com.interview.javabinterview.dubbo.api.service.MyServiceAPI;
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
    private MyServiceAPI service;

    public String print(String name){
        return service.print(name);
    }
}
