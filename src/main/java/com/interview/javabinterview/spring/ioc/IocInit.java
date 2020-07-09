package com.interview.javabinterview.spring.ioc;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class IocInit {

    public static void main(String[] args) {
        method_1();//IOC 容器初始化过程入口
    }

    /**
     * 初始化入口: 在AbstractApplicationContext.refresh()方法;
     */
    private static void method_1() {
        FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext();
        applicationContext.refresh();
    }
}
