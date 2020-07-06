package com.interview.javabinterview.dubbo.example.xml;

import com.interview.javabinterview.dubbo.example.xml.service.MyServiceXML;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo 客户端:基于XML配置
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class ClientTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-dubbo-consumer.xml");
        context.start();

        MyServiceXML myService = (MyServiceXML) context.getBean("myServiceImpl");
        myService.print("jubaoquan");
    }
}
