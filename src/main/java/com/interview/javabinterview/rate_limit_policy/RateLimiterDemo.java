package com.interview.javabinterview.rate_limit_policy;

import com.interview.javabinterview.rate_limit_policy.mq.MyAppConsumer;
import com.interview.javabinterview.rate_limit_policy.mq.MyAppProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1.消费者启动,创建 RateLimitPolicy: 初始化ScheduledThreadPoolExecutor 线程池
 * 2.每发一条消息先调用 rateLimitPolicy.execute()方法
 * 1).调用execute():
 * 判断ScheduledFuture ==null 是
 * ScheduledFuture<?>  = ScheduledThreadPoolExecutor.scheduleAtFixedRate(
 * 1.每秒重置messageCount = 0
 * 2.notifyAll();
 * )
 * 否: 直接跳过
 * <p>
 * 2).判断messageCount > maxTps 是 wait() ; 否:什么都不做
 * <p>
 * <p>
 * 3. 调用固定频率(1s)执行的方法. 定时1s更新 发送消息累加个数.为0. 然后
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class RateLimiterDemo {

    private static BlockingQueue<String> MESSAGE_QUEUE = new LinkedBlockingQueue<>();

    private static long messageId = 0;

    public static void main(String[] args) throws InterruptedException {
        sendMessage();

        TimeUnit.SECONDS.sleep(2);
        System.out.println("此时有" + MESSAGE_QUEUE.size() + "条消息");

        MyAppConsumer consumer = new MyAppConsumer(MESSAGE_QUEUE, 100);
        consumer.consume(RateLimiterDemo::handle);
    }

    private static void handle(String msg) {
        System.out.println(msg);
    }

    private static void sendMessage() {
        MyAppProducer producer = new MyAppProducer(MESSAGE_QUEUE, 120);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    messageId++;
                    producer.sendMessage("消息:" + messageId);
                }
            }
        }).start();
    }
}
