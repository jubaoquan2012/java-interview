package com.interview.javabinterview.rate_limit_policy.ratelimit;

import com.interview.javabinterview.rate_limit_policy.util.Preconditions;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * consumer 限流策略
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class ConsumerRateLimiter {

    private final ScheduledExecutorService executorService;

    private long rateTime;

    private TimeUnit timeUnit;

    private final boolean externalExecutor;

    private boolean isClosed;

    private ScheduledFuture<?> renewTask;

    private long permits;

    private long acquiredPermits;

    public ConsumerRateLimiter(long permits, long rateTime, TimeUnit timeUnit) {
        this((ScheduledExecutorService) null, permits, rateTime, timeUnit);
    }

    public ConsumerRateLimiter(ScheduledExecutorService executorService, long permits, long rateTime, TimeUnit timeUnit) {
        Preconditions.checkArgument(permits > 0L, "permits" + permits + "参数不合法");
        Preconditions.checkArgument(rateTime > 0L, "rateTime" + rateTime + "参数不合法");
        this.rateTime = rateTime;
        this.timeUnit = timeUnit;
        this.permits = permits;
        if (executorService != null) {
            this.executorService = executorService;
            this.externalExecutor = true;
        } else {
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            /**
             * 设置在线程池关闭时，周期任务继续执行，默认为false，也就是线程池关闭时，不再执行周期任务。
             *  如果为 true，则在关闭后继续执行；否则不执行。
             */
            executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
            /**
             * 设置在线程池关闭时，延迟任务继续执行，默认为false，也就是线程池关闭时，不再执行延迟任务。
             * 如果为 true，则在关闭后继续执行；否则不执行。
             */
            executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            this.executorService = executor;
            this.externalExecutor = false;
        }
    }

    public synchronized void acquire() throws InterruptedException {
        this.acquire(1L);
    }

    public synchronized void acquire(long acquirePermit) throws InterruptedException {
        Preconditions.checkArgument(!this.isClosed(), "RateLimiter is already shutdown");
        Preconditions.checkArgument(acquirePermit <= this.permits, "acquiring permits must be less or equal than initialized rate =" + this.permits);
        if (this.renewTask == null) {
            this.renewTask = this.createTask();
        }

        boolean canAcquire = false;

        do {
            canAcquire = acquirePermit < 0L || this.acquiredPermits < this.permits;
            if (!canAcquire) {
                this.wait();
            } else {
                this.acquiredPermits += acquirePermit;
            }
        } while (!canAcquire);
    }

    public synchronized boolean tryAcquire() {
        return this.tryAcquire(1L);
    }

    public synchronized boolean tryAcquire(long acquirePermit) {
        Preconditions.checkArgument(!this.isClosed(), "Rate limiter is already shutdown");
        if (this.renewTask == null) {
            this.renewTask = this.createTask();
        }

        if (acquirePermit > this.permits) {
            this.acquiredPermits = this.permits;
            return false;
        } else {
            boolean canAcquire = acquirePermit < 0L || this.acquiredPermits < this.permits;
            if (canAcquire) {
                this.acquiredPermits += acquirePermit;
            }

            return canAcquire;
        }
    }

    protected ScheduledFuture<?> createTask() {
        return this.executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                ConsumerRateLimiter.this.renew();
            }
        }, this.rateTime, this.rateTime, this.timeUnit);
    }

    synchronized void renew() {
        this.acquiredPermits = 0L;
        this.notifyAll();
    }

    public synchronized boolean isClosed() {
        return this.isClosed;
    }

    public synchronized void close() {
        if (!this.isClosed) {
            if (!this.externalExecutor) {
                this.executorService.shutdownNow();
            }

            if (this.renewTask != null) {
                this.renewTask.cancel(false);
            }

            this.isClosed = true;
        }
    }
}
