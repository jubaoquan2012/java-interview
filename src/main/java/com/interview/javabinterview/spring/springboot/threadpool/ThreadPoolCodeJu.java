package com.interview.javabinterview.spring.springboot.threadpool;

import com.interview.javabinterview.spring.springboot.threadpool.impl.ThreadPoolExecutor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 类
 * 线程池 参考:https://www.cnblogs.com/liuzhihu/p/8177371.html
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class ThreadPoolCodeJu {

    public static void main(String[] args) {
        method_1();//线程池构造参数
        method_2();//线程池拒绝策略
        method_3();//线程池的状态
        method_4();//工作队列和线程数量相互影响
    }

    private static void method_1() {
        BasicThreadFactory threadFactory = new BasicThreadFactory
                .Builder()
                .namingPattern(String.format("pulsarConsumerPool-%s-%%d", 1000))
                .build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,                               // 核心线程数
                1,                           // 最大线程数
                1,                              // 空闲线程存活时间
                TimeUnit.MINUTES,                            // 时间单位
                new LinkedBlockingQueue<>(100),      // 等待队列
                threadFactory,                              //  线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        threadPoolExecutor.allowCoreThreadTimeOut(true);//设置核心线程可以销毁

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程执行");
            }
        });
    }

    private static void method_2() {
        /**
         * 1. AbortPolicy 默认拒绝策略: 直接抛出异常:RejectedExecutionException
         *      AbortPolicy.rejectedExecution(Runnable r, ThreadPoolExecutor e){
         *              throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
         *      }
         *
         * 2.CallerRunsPolicy 用调用者所在的线程来执行任务
         *      CallerRunsPolicy.rejectedExecution(Runnable r, ThreadPoolExecutor e){
         *           if (!e.isShutdown()) {
         *             r.run();
         *         }
         *      }
         * 3.DiscardPolicy 直接抛弃任务,不执行
         *      DiscardPolicy.rejectedExecution(Runnable r, ThreadPoolExecutor e){
         *
         *      }
         *
         * 4.DiscardOldestPolicy:从队列里面抛弃head的一个任务，并执行当前任务
         *      DiscardOldestPolicy.rejectedExecution(Runnable r, ThreadPoolExecutor e){
         *          if (!e.isShutdown()) {
         *             e.getQueue().poll();
         *             e.execute(r);
         *         }
         *      }
         *
         */
    }

    /**
     * 线程池的状态
     */
    private static void method_3() {
        /**
         * RUNNING:
         *  1.线程池此状态时，能够接收新任务，以及对已添加的任务进行处理。
         *  2. 线程池的初始化状态是RUNNING。换句话说，线程池被一旦被创建，就处于RUNNING状
         *      态，并且线程池中的任务数为0！
         *
         * SHUTDOWN:
         *  1.线程池此状态时，不接收新任务，但能处理已添加的任务。
         *  2.调用线程池的shutdown()接口时，线程池由RUNNING -> SHUTDOWN。
         *
         * STOP:
         *  1.线程池处在STOP状态时，不接收新任务，不处理已添加的任务，并且会中断正在处理的任务。
         *  2.调用线程池的shutdownNow()接口时，线程池由(RUNNING or SHUTDOWN ) -> STOP。
         *
         * TIDYING:
         *  1.如果所有的任务都已终止了，workerCount (有效线程数) 为0，线程池进入该状态后会调用 terminated() 方法进入TERMINATED 状态。
         *  2.状态转换
         *   1).当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也为空时，就会由 SHUTDOWN -> TIDYING。
         *   2).当线程池在STOP状态下，线程池中执行的任务为空时，就会由STOP -> TIDYING。
         *
         * TERMINATED:
         *  在terminated() 方法执行完后进入该状态，默认terminated()方法中什么也没有做。
         *  进入TERMINATED的条件如下：
         *      .线程池不是RUNNING状态；
         *      .线程池状态不是TIDYING状态或TERMINATED状态；
         *      .如果线程池状态是SHUTDOWN并且workerQueue为空；
         *      .workerCount为0；
         *      .设置TIDYING状态成功。
         */
    }

    /**
     * 工作队列和线程数量相互影响
     */
    private static void method_4() {
        /**
         * 前提:
         * 1.线程入队顺序(假设任务足够多): 创建核心线程(corePoolSize)-->入阻塞队列(workQueue)-->创建最大线程(maximumPoolSize)
         * 2.corePoolSize:    10
         *   maximumPoolSize: 100
         *   workQueue:
         *
         *      1).使用无界队列 LinkedBlockingQueue:
         *          --workQueue 无界, 阻塞队列(workQueue)不会满(一定程度),就不会再创建(maximumPoolSize)线程了.
         *          --所有的任务都在阻塞队列里等待
         *
         *      2).使用有界队列 ArrayBlockingQueue<>(100):
         *          --workQueue 有界, 阻塞队列(workQueue)会满,会再创建(maximumPoolSize)线程
         *          --所以需要合理的调整workQueue 和 maximumPoolSize的大小
         *             .如果要想降低系统资源的消耗（包括CPU的使用率，操作系统资源的消耗，上下文环境切换的开销等）, 可以设置较大的队列容量和
         *              较小的线程池容量, 但这样也会降低线程处理任务的吞吐量。
         *             .如果提交的任务经常发生阻塞，那么可以考虑通过调用 setMaximumPoolSize() 方法来重新设定线程池的容量。
         *             .如果队列的容量设置的较小，通常需要将线程池的容量设置大一点，这样CPU的使用率会相对的高一些。但如果线程池的容量设置的
         *              过大，则在提交的任务数量太多的情况下，并发量会增加，那么线程之间的调度就是一个要考虑的问题，因为这样反而有可能降低处理任务的吞吐量。
         */
    }
}

