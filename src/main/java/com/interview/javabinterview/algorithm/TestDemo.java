package com.interview.javabinterview.algorithm;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/12/11
 */
public class TestDemo {
    //实现交替打印类
    public static class ThreadPrint implements Runnable{

        /**要打印的值*/
        private final String value;

        /**当前对象前一个对象(作为锁对象)*/
        private final Object prev;

        /**当前对象(作为锁对象)*/
        private final Object current;

        public ThreadPrint(String value,Object prev,Object current){
            this.value = value;
            this.prev = prev;
            this.current = current;
        }

        @Override
        public void run(){
            int count = 100;
            while(count > 0){
                synchronized(prev){
                    synchronized(current){
                        //System.out.println(value+":"+Thread.currentThread().getName());
                        //System.out.print(value);
                        System.out.print(value);
                        count--;
                        current.notifyAll();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        printStart();
    }

    //交替打印线程启动方法
    public static void printStart() throws InterruptedException {
        Object obj_1 = new Object();
        Object obj_2 = new Object();
        Object obj_3 = new Object();
        ThreadPrint tp_1 = new ThreadPrint("a",obj_3,obj_1);
        ThreadPrint tp_2 = new ThreadPrint("l",obj_1,obj_2);
        ThreadPrint tp_3 = new ThreadPrint("i",obj_2,obj_3);

        new Thread(tp_1,"t1").start();
        TimeUnit.MILLISECONDS.sleep(100);
        new Thread(tp_2,"t2").start();
        TimeUnit.MILLISECONDS.sleep(100);
        new Thread(tp_3,"t3").start();
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
