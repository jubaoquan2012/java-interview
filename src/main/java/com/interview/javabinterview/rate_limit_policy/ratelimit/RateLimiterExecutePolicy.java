package com.interview.javabinterview.rate_limit_policy.ratelimit;

import java.util.concurrent.TimeUnit;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class RateLimiterExecutePolicy implements ExecutePolicy {

    private ConsumerRateLimiter rateLimiter;

    public RateLimiterExecutePolicy(int maxTps) {
        rateLimiter = new ConsumerRateLimiter(maxTps, 1L, TimeUnit.SECONDS);
    }

    @Override
    public boolean executable() throws InterruptedException {
        rateLimiter.acquire();
        return true;
    }

    @Override
    public long sleepMillis() {
        return 0;
    }

    @Override
    public boolean isSuspended() {
        return false;
    }
}
