package com.interview.javabinterview.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shineng.he
 * @Date 2019/8/20 14:05
 */
public class ThreadTest5 {
    /**
     * 使用多线程方式，计算1到100 之间求和 ，线程个数可以参数控制
     */

    public static void main(String[] args) {
        ThreadTest5 threadTest5=new ThreadTest5();
        //threadTest5.calculationTest1(7,100);
        threadTest5.calculationTest2(7,100);
    }

    private int calculationNormal(int maxNum){
        int value=0;
        for(int i=1;i<=100;i++){
            value+=i;
        }
        return value;
    }

    private volatile int finalValue=0;

    private LongAdder longAdder=new LongAdder();

    /**
     * 思路：将1到n加 平均分到 m个线程中，每个线程中先做连续的自加，再做线程之间的求和
     *
     * 至于 计算的 线程数量和最大数是否合法，暂时不做校验
     *
     * @param threadNum 线程数量
     * @param maxNum 加到的最大值
     */
    private void calculationTest1(int threadNum,int maxNum){
        //分组数量
        int groupNum=threadNum;
        //每组分组的数量
        int groupItemNum=maxNum/threadNum;
        //最后执行完所有线程执行完，与正确数据做对比
        CountDownLatch countDownLatch=new CountDownLatch(groupNum);
        // 计数等待所有线程真实真正执行
        CyclicBarrier cyclicBarrier=new CyclicBarrier(groupNum);
        for(int i=0;i<groupNum;i++){
            final int executeIndex=i;
            //执行每一个线程
            new Thread(()->{
                try {
                    //等待所有线程都创建好，再一起执行
                    cyclicBarrier.await();
                    //计算循环周期
                    int start=executeIndex*groupItemNum;
                    int end=start+groupItemNum;
                    if(start==0){
                        //让起始值从1 开始自加
                        start=1;
                    }
                    if(executeIndex==groupNum-1){
                        //最后一次循环，将所有数据加上
                        end=maxNum+1;//使得能加最后一个数据加上
                    }
                    int resultValue=0;
                    for(;start<end;start++){
                        resultValue+=start;
                    }
                   // System.out.println("executeIndex:"+executeIndex+" resultValue:"+resultValue);
                    //等到每一个线程的加的值，加结果加到全局变量中去 一种使用 longAdder 一种加锁
                    longAdder.add(resultValue);
                    //计算完毕，等待其他线程执行完
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        //等待所有线程执行完，比对计算结果
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int threadResult=longAdder.intValue();
        int normalResult=calculationNormal(maxNum);
        System.out.println("多线程计算值："+threadResult+" 正确结果："+normalResult);
    }

    /**
     * 使用线程池去加减每一个任务
     * @param threadNum
     * @param maxNum
     */
    private void calculationTest2(int threadNum,int maxNum){
        ExecutorService executorService=Executors.newFixedThreadPool(threadNum);
        ReentrantLock reentrantLock=new ReentrantLock(true);
        List<Future<Integer>> list=new ArrayList<>();
        for(int i=1;i<=maxNum;i++){
            final int num=i;
            Future<Integer> future =executorService.submit(()->{
                //这种方式执行太慢，可以改进 longAdder
                try {
                    reentrantLock.lock();
                    finalValue = finalValue + num;
                    return 0;
                }finally {
                    reentrantLock.unlock();
                }
            });
            list.add(future);
        }

        // 确保所有线程执行完
        list.forEach(item->{
            try {
                item.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        int threadResult=finalValue;
        int normalResult=calculationNormal(maxNum);
        System.out.println("多线程计算值："+threadResult+" 正确结果："+normalResult);

    }

}
