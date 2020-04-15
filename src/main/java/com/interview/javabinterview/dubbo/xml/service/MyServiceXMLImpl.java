package com.interview.javabinterview.dubbo.xml.service;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class MyServiceXMLImpl implements MyServiceXML {

    @Override
    public String print(String name) {
        System.out.println(name);
        return name + System.currentTimeMillis();
    }
}
