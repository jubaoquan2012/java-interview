package com.interview.javabinterview.thread.interrupt;

/**
 * void interrupt():      设置线程的中断标志
 * boolean isInterrupted():  获取线程的中断状态 :当前的状态:不清除线程中断状态
 * boolean interrupted():    获取线程的中断状态 :当前的状态:清除线程中断状态
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Thread_Interrupted_state extends Thread {
    @Override
    public void run() {
        System.out.println("run运行");
        while (true) {

        }
    }

    public static void main(String[] args) {
        Thread_Interrupted_state td = new Thread_Interrupted_state();
        System.out.println("1_状态为_" + td.isInterrupted());//false 主线程运行
        //线程启动
        td.start();
        try {
            //保证td 中的run()执行完
            Thread.sleep(1000);
            System.out.println("2_状态为_" + td.isInterrupted());//false
            //设置线程中断状态为 true
            td.interrupt();
            System.out.println("3_状态为_" + td.isInterrupted());//true 不清除状态
            System.out.println("4_状态为_" + td.isInterrupted());//true
            //获取线程中断状态,并且清除中断状态
            System.out.println("5_状态为_" + Thread.interrupted());//false 清除状态(状态复位)
            System.out.println("6_状态为_" + Thread.interrupted());//false 清除状态(状态复位)
            System.out.println("7_状态为_" + td.isInterrupted());//true 不清除状态
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
