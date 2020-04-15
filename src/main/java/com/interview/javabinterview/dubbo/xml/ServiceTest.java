package com.interview.javabinterview.dubbo.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo 服务端 :基于XML配置
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class ServiceTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicaiton-service.xml");
        context.start();
    }
}
