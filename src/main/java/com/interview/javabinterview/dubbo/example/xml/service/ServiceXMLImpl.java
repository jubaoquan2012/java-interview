package com.interview.javabinterview.dubbo.example.xml.service;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ServiceXMLImpl implements ServiceXML {

    @Override
    public String print(String name) {
        System.out.println(name);
        return name + System.currentTimeMillis();
    }
}
