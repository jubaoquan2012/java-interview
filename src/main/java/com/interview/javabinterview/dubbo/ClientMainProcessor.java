package com.interview.javabinterview.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ClientMainProcessor {

    private static Object WAITOBJECT = new Object();

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("application-client.xml");
        MyService myService = (MyService) app.getBean("myService");

        myService.doMyTest("1234","root");

        synchronized (ClientMainProcessor.WAITOBJECT){
            ClientMainProcessor.WAITOBJECT.wait();
        }
    }
}
