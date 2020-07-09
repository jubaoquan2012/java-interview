package com.interview.javabinterview.spring.application;

import com.interview.javabinterview.spring.application.aware.AwareConfig;
import com.interview.javabinterview.spring.application.aware.AwareService;
import com.interview.javabinterview.spring.application.event.DemoPublisher;
import com.interview.javabinterview.spring.application.event.EventConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.method_1();//spring的事件(Application Event)
        main.method_2();//spring Aware
    }

    /**
     * spring的事件(Application Event)
     * <p>
     * 为 Bean 与 Bean 之间的消息通信提供支持. 当一个Bean处理完一个任务之后,希望另外一个Bean知道并能做响应的处理.
     * 这时就需要让另外一个Bean监听当前Bean所发送的事件
     */
    private void method_1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        DemoPublisher publisher = context.getBean(DemoPublisher.class);
        publisher.publish("hello application event");
        context.close();
    }

    private void method_2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);
        AwareService awareService = context.getBean(AwareService.class);
        awareService.outputResult();
        context.close();
    }
}
