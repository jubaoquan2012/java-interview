package com.interview.javabinterview.dubbo.example.anno;

import com.interview.javabinterview.dubbo.example.anno.service.configuration.ServiceConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class ServiceTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfiguration.class);
        context.start();
    }
}
