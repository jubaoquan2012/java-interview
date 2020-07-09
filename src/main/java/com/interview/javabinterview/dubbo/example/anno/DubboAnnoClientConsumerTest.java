package com.interview.javabinterview.dubbo.example.anno;

import com.interview.javabinterview.dubbo.example.anno.client.configuration.ClientConfiguration;
import com.interview.javabinterview.dubbo.example.anno.client.MyServiceClientAPI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class DubboAnnoClientConsumerTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);
        context.start();
        MyServiceClientAPI client = context.getBean(MyServiceClientAPI.class);
        String str = client.print("jubaoquan");
        System.out.println(str);
    }
}
