package com.interview.javabinterview.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ServiceMainProcessor {

    private static Object WAITOBJECT = new Object();

    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("application-service.xml");

        /**
         * 这里锁定这个应用程序，和DUBBO框架本身的工作原理没有任何关系，只是为了让其不退出
         */
        synchronized (ServiceMainProcessor.WAITOBJECT) {
            ServiceMainProcessor.WAITOBJECT.wait();
        }
    }
}
