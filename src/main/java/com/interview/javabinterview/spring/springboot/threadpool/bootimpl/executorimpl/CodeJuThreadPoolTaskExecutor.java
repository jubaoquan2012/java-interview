package com.interview.javabinterview.spring.springboot.threadpool.bootimpl.executorimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class CodeJuThreadPoolTaskExecutor extends ThreadPoolTaskExecutor  {

    private static final Logger logger = LoggerFactory.getLogger(CodeJuThreadPoolTaskExecutor.class);

    private void showThreadPollInfo() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        if (null == threadPoolExecutor) {
            return;
        }
        logger.info("{},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        showThreadPollInfo();
        System.out.println("打印任务.1");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPollInfo();
        System.out.println("打印任务.2");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPollInfo();
        System.out.println("打印任务.3");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPollInfo();
        System.out.println("打印任务.4");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPollInfo();
        System.out.println("打印任务.5");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPollInfo();
        System.out.println("打印任务.6");
        return super.submitListenable(task);
    }
}
