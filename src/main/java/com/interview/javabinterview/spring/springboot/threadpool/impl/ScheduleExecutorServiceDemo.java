package com.interview.javabinterview.spring.threadpool.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class ScheduleExecutorServiceDemo {

    /**
     * 固定频率执行的线程
     * <p>
     * scheduleAtFixedRate() 四个参数:
     * <p>
     * 1.执行线程
     * public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
     * <p>
     * 2.初始化延时
     * long initialDelay,
     * <p>
     * 3.两次开始执行最小间隔时间
     * long period,
     * <p>
     * 4.计时单位
     * TimeUnit unit);
     */

    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {


        /**
         * 如果 period > 程序执行时间. 固定每period时间执行
         * 如果 period < 程序执行时间. 则会覆盖period, 以程序真实执行时间开始
         */
        //scheduleAtFixedRate
        executor.scheduleAtFixedRate(new Runnable() {
            //模拟耗时任务,耗时是10s以内的任意数
            @Override
            public void run() {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    System.out.print(sdf.format(new Date()) + " 开始执行, ");
                     long executeTime = (long) (Math.random() * 10)*1000;
                    //final long executeTime = 3000L;
                    Thread.sleep(executeTime);//3s// 程序真正执行时间
                    System.out.println(sdf.format(new Date()) + "结束执行 ================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);//每隔5s
    }
}
