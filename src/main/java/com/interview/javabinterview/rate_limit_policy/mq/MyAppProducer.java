package com.interview.javabinterview.rate_limit_policy.mq;

import com.interview.javabinterview.rate_limit_policy.ratelimit.ExecutePolicy;
import com.interview.javabinterview.rate_limit_policy.ratelimit.RateLimiterExecutePolicy;

import java.util.concurrent.BlockingQueue;

/**
 * producer
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class MyAppProducer implements MyProducer {

    private BlockingQueue<String> BrokerQueue;

    private ExecutePolicy executePolicy;

    public MyAppProducer(BlockingQueue<String> BrokerQueue) {
        this.BrokerQueue = BrokerQueue;
    }

    public MyAppProducer(BlockingQueue<String> BrokerQueue, int maxTps) {
        this.BrokerQueue = BrokerQueue;
        if (maxTps > 0) {
            executePolicy = new RateLimiterExecutePolicy(maxTps);
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            if (executePolicy != null && !executePolicy.executable()) {
                // todo 暂时什么都不做
            }
            BrokerQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
