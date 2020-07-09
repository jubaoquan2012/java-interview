package com.interview.javabinterview.dubbo.example.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Dubbo 服务端 :基于XML配置
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class DubboXmlServiceProviderTest {

    /**
     *
     */
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-dubbo-provider.xml");
        context.start();

        System.in.read();
    }
}
