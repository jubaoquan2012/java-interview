package com.interview.javabinterview.rate_limit_policy.mq;

import com.interview.javabinterview.rate_limit_policy.ratelimit.ExecutePolicy;
import com.interview.javabinterview.rate_limit_policy.ratelimit.RateLimiterExecutePolicy;
import com.interview.javabinterview.rate_limit_policy.util.Preconditions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * consuemr
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class MyAppConsumer implements MyConsumer {

    private BlockingQueue<String> blockingQueue;

    private ExecutePolicy executePolicy;

    public MyAppConsumer(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public MyAppConsumer(BlockingQueue<String> blockingQueue, int maxTps) {
        this.blockingQueue = blockingQueue;
        if (maxTps > 0) {
            executePolicy = new RateLimiterExecutePolicy(maxTps);
        }
    }

    public void consume(Consumer<String> callback) {
        doConsume(callback);
    }

    private void doConsume(Consumer<String> callback) {
        try {
            while (true) {
                if (executePolicy != null && !executePolicy.executable()) {
                    long sleepMillis = executePolicy.sleepMillis();
                    if (executePolicy.isSuspended()) {
                        suspend(sleepMillis);
                    } else {
                        TimeUnit.MILLISECONDS.sleep(sleepMillis);
                    }
                }
                callback.accept(blockingQueue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void suspend(long sleepMillis) throws InterruptedException {
        closeConsumer();
        TimeUnit.MILLISECONDS.sleep(sleepMillis);
        createConsumer();
    }

    private void closeConsumer() {

    }

    private void createConsumer() {
    }
}
