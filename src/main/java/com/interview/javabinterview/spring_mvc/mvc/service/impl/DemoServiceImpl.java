package com.interview.javabinterview.spring_mvc.mvc.service.impl;


import com.interview.javabinterview.spring_mvc.framework.annotation.GPService;
import com.interview.javabinterview.spring_mvc.mvc.service.DemoService;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/26
 */
@GPService
public class DemoServiceImpl implements DemoService {
    @Override
    public String query(String name) {
        return "My name is: "+name;
    }
}
