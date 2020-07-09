package com.interview.javabinterview.dubbo.example.anno;

import com.interview.javabinterview.dubbo.example.anno.service.configuration.ServiceConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class DubboAnnoServiceProviderTest {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfiguration.class);
        context.start();
        System.in.read();
    }
}
