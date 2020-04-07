package com.interview.javabinterview.rate_limit_policy.ratelimit;

/**
 * 接口
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public interface ExecutePolicy {

    /**
     * 判断当前时间点是否可执行
     *
     * @return 是否可执行
     * @throws InterruptedException 中断异常
     */
    boolean executable() throws InterruptedException;

    /**
     * 休眠时间间隔
     *
     * @return 休眠毫秒数
     */
    long sleepMillis();

    /**
     * 是否挂起当前消费者
     *
     * @return 是否挂起
     */
    boolean isSuspended();
}
