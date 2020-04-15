package com.interview.javabinterview.dubbo.anno;

import com.interview.javabinterview.dubbo.anno.client.configuration.ClientConfiguration;
import com.interview.javabinterview.dubbo.anno.client.MyServiceClientAPI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class ClientTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);
        context.start();
        MyServiceClientAPI client = context.getBean(MyServiceClientAPI.class);
        String str = client.print("jubaoquan");
        System.out.println(str);
    }
}
