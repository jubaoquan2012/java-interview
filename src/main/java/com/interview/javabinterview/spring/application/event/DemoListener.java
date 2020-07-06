package com.interview.javabinterview.spring.applicationevent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMsg();
        System.out.println("我(bean-DemoListener)接收到了bean-demoPublisher发布的消息:" + msg);
    }
}
