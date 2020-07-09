package com.interview.javabinterview.dubbo.example.xml;

import com.interview.javabinterview.dubbo.example.xml.service.ServiceXML;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Dubbo 客户端:基于XML配置
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class DubboXmlClientConsumerTest {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-dubbo-consumer.xml");
        context.start();

        ServiceXML myService = (ServiceXML) context.getBean("myService");
        myService.print("jubaoquan");
    }
}
