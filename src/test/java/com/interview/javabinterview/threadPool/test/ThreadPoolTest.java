package com.interview.javabinterview.threadPool.test;




import com.interview.javabinterview.threadPool.TaskIndex;
import com.interview.javabinterview.threadPool.ThreadPoolRunnableWorker;

import java.util.concurrent.*;

/**
 * @author shineng.he
 * createTime 2018/9/11 16:01
 */

public class ThreadPoolTest {

    /**
     * 注： 先在此处记一下结论
     *  线程池 ThreadPoolExecutor 核心参数
     *  corePoolSize 核心线程数
     *  maximumPoolSize 最大线程数
     *  keepAliveTime 线程空闲消亡时间
     *  TimeUnit 时间
     *  workQueue（BlockingQueue -LinkedBlockingQueue 或者 SynchronousQueue ArrayQueue 这个稍后弄清楚）
     *  threadFactory 线程池的线程创建工厂
     *  RejectedExecutionHandler 当线程数到达最大值时，并且等待的任务队列已满时，再有新的任务到来是的 拒绝策略
     *
     *  当往线程池添加任务时，及调用 execute的执行方法时：
     *
     *  最开始： 线程池的线程数为0 ，任务队列为0，活动线程为0，完成任务为0
     *
     *  当加人第一个任务时，会线程池启动一个线程分配，此时线程数为1 活动线程为1 任务队列为0，完成任务为0，
     *  记住只要：  有任务加入 未分配线程的任务，才会留在任务队列中，已分配的，从任务队列剔除
     *
     *  当再有其他任务加入时，判断当前线程池中线程数小于核心线程数时，任务队列未满时，会增加新线程给新任务分配，
     *
     *  注意（重点）：直到线程池线程数等于核心线程数时，线程数暂时不再添加，直到任务持续添加，到任务队列满时；
     *  （一旦当前线程池数量到达了 核心线程数量时，当任务队列不满时，再添加新任务也只会再队列中，不会开启新线程去消费队列任务，
     *  所以只有当线程任务满时，再有任务添加才会开启新线程，直到线程池中线程数量达到最大线程数时，再有任务，且任务队列已满时，触发拒绝策略）
     *
     *  再有任务添加，会增加线程池中线程，直到线程池线程数到达最大时，且任务队列已满，再有任务添加时，
     *
     *  会触发线程池的拒绝策略：
     *  1.AbortPolicy 抛出异常，默认的拒绝策略 （抛出 RejectedExecutionException的异常）
     *  2.DiscardPolicy 不抛异常，舍弃新加入的任务
     *  3.DiscardOldestPolicy 不抛异常，舍弃最早加入的任务
     *  4.CallerRunsPolicy 不抛异常，由当前调用线程自己执行此次任务（相当于阻塞调用线程）
     *haode
     *  当任务都分给给线程，使得任务队列为0时，此时无任务分配，执行完的任务的线程会等待
     *  keepAliveTime 时间后，慢慢销毁线程，直到线程收缩到核心线程数，然后重复此过程
     */

    /**
     * 关于线程池中，等待任务队列满了之后，如何处理再添加的任务
     */
    private ThreadPoolExecutor createThreadPool(RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                4, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(2), rejectedExecutionHandler);
        return threadPoolExecutor;
    }

    private void recordPrint(ThreadPoolExecutor threadPoolExecutor, TaskIndex taskIndex) {
        // 打印目前线程池的状态
        System.out.println(System.currentTimeMillis() + " " + threadPoolExecutor.toString());
        recordWaitQueue(threadPoolExecutor);
        if (threadPoolExecutor.getQueue().size() == 0 && threadPoolExecutor.getPoolSize() == 2) {
            /**
             * 等待任务已处理完，等待序列为空，线程池中线程数收缩到 核心线程2 个 是即将结束的标志
             * 连续5次处于该状态，代表已经处于空闲状态
             */
            System.out.println("当前TaskIndex:" + taskIndex.addTaskIndex+"--"+threadPoolExecutor.toString());
            taskIndex.taskStopFlag++;
        }
        if (taskIndex.addTaskIndex >= 19) {
            //任务数大于18个以后，流出一点空闲时间休息，并等待线程池处理完任务
            sleep(2000);
        }
    }

    private void recordWaitQueue(ThreadPoolExecutor threadPoolExecutor) {
        BlockingQueue<Runnable> workQueue = threadPoolExecutor.getQueue();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = (LinkedBlockingQueue<Runnable>) (workQueue);
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Runnable item : linkedBlockingQueue) {
            ThreadPoolRunnableWorker worker = (ThreadPoolRunnableWorker) item;
            builder.append(worker.getTaskCode());
            builder.append(",");
        }
        builder.append("]");
        System.out.println("等待队列数据：" + builder.toString());

    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ThreadPoolTest test = new ThreadPoolTest();
        //test.abortPolicyTest();
        test.discardPolicyTest();
        //test.discardOldestPolicyTest();
        //test.CallerRunsPolicyTest();
    }

    /**
     * 拒绝策略
     * 以核心数=2 最大线程=4，任务队列size =2
     * 首先启动，添加一个任务 线程池线程=1 激活线程=1 加入的任务队列的任务被消费=0 完成任务=0
     * 继续添加任务 增加线程池线程，到线程池线程的核心数2，线程暂时增加，任务队列任务被堆积，直达
     * 达到最大任务数2
     * <p>
     * 及 pool size = 2, active threads = 2, queued tasks = 2, completed tasks = 0
     * <p>
     * 此时再加任务，线程池会启动新线程，任务队列任务被消费，
     * 直到 最大线程数，再加任务，任务队列会堆积任务，直到达到最大任务数
     * <p>
     * 及 pool size = 4, active threads = 4, queued tasks = 2, completed tasks = 0
     * <p>
     * 再增加任务会触发拒绝策略
     * <p>
     * 这种逻辑适用各种策略的线程池方式
     * <p>
     * abort 发生拒绝时抛异常，在触发时，如以下代码：
     * <p>
     * 需要注意 发生的异常是发生在 threadPoolExecutor.execute() 发生在线程池中调度执行方法，不发生内部Runnable方法中
     * <p>
     * 此外如果不对异常处理，抛出异常时，程序停止;
     * 如果处理异常 ,继续添加任务 在任务队列的任务=2，没有消费完之前，会持续抛异常，
     * 如下面的任务执行次数 循环19 仍会继续执行
     * <p>
     * 所以看新的任务是否能继续执行，主意看新的任务是否能加入到任务队列中（若为最大线程数，队列已满会持续抛异常）
     * <p>
     * 若再添加任务时，前面任务执行完，可以继续添加 （类似没发生异常之前的状态，及没有达到发生异常的临界点）
     */
    private void abortPolicyTest() {
        ThreadPoolExecutor threadPoolExecutor = createThreadPool(new ThreadPoolExecutor.AbortPolicy());
        TaskIndex taskIndex = new TaskIndex();
        new Thread(() -> {
            for (; ; ) {
                if (taskIndex.addTaskIndex < 19) {
                    taskIndex.addTaskIndex++;
                    System.out.println("addTaskIndex=" + taskIndex.addTaskIndex);
                    ThreadPoolRunnableWorker threadPoolRunnableWorker = new ThreadPoolRunnableWorker(taskIndex.addTaskIndex);
                    try {
                        threadPoolExecutor.execute(threadPoolRunnableWorker);
                    } catch (RejectedExecutionException e) {
                        //e.printStackTrace();
                        System.out.println(e);
                        // 任务拒绝策略的 发生的序号
                        System.out.println("任务拒绝策略的 发生的序号" + threadPoolRunnableWorker.getTaskCode());
                        break;
                    }
                }
                recordPrint(threadPoolExecutor, taskIndex);
                if (taskIndex.taskStopFlag >= 5) {
                    // 空闲任务结束
                    break;
                }
            }
            //测试执行完毕，关闭线程池
            threadPoolExecutor.shutdown();
        }).start();
    }

    /**
     * 执行过程 参照abort ，策略触发时机参照上面
     * discardPolicy 触发策略时 舍弃将要加入的新任务，继续执行之前的老任务
     * <p>
     * 及最大线程数 队列已满时，由于策略，新的任务加不到任务队列中去，
     * <p>
     * 一直加新任务，会一直舍弃
     * <p>
     * 直到任务队列中有任务执行完，再打破触发策略的临界值时，即可加入新的任务
     * <p>
     * 循环加入19次的任务，仍然会持续执行
     */
    private void discardPolicyTest() {
        ThreadPoolExecutor threadPoolExecutor = createThreadPool(new ThreadPoolExecutor.DiscardPolicy());
        commonThreadRun(threadPoolExecutor);
    }

    /**
     * 执行过程 参照abort ，策略触发时机参照上面
     * discardOldestPolicy 触发策略时 会舍弃之前最老加入的任务队列，执行新加入的任务
     * 及 最大线程数 队列已满时，由于策略:
     * 注：舍弃的任务是还留存在任务队列中最老任务（及没有被线程消费的任务），再往任务队列加入新任务
     * 已经被线程执行的任务不会消费，
     * <p>
     * 这个可以不停增加新任务，舍弃很多老任务
     * <p>
     * 循环加入19次的任务，仍然会持续执行
     */
    private void discardOldestPolicyTest() {
        ThreadPoolExecutor threadPoolExecutor = createThreadPool(new ThreadPoolExecutor.DiscardOldestPolicy());
        commonThreadRun(threadPoolExecutor);

    }

    private void CallerRunsPolicyTest() {
        ThreadPoolExecutor threadPoolExecutor = createThreadPool(new ThreadPoolExecutor.CallerRunsPolicy());
        commonThreadRun(threadPoolExecutor);

    }

    private void commonThreadRun(ThreadPoolExecutor threadPoolExecutor) {
        TaskIndex taskIndex = new TaskIndex();
        new Thread(() -> {
            for (; ; ) {
                if (taskIndex.addTaskIndex < 19) {
                    taskIndex.addTaskIndex++;
                    System.out.println("addTaskIndex=" + taskIndex.addTaskIndex);
                    ThreadPoolRunnableWorker threadPoolRunnableWorker = new ThreadPoolRunnableWorker(taskIndex.addTaskIndex);
                    threadPoolExecutor.execute(threadPoolRunnableWorker);
                }
                recordPrint(threadPoolExecutor, taskIndex);
                if (taskIndex.taskStopFlag >= 5) {
                    // 空闲任务结束
                    break;
                }
            }
            //测试执行完毕，关闭线程池
            threadPoolExecutor.shutdown();
        }).start();
    }


}
