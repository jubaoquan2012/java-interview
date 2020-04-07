package com.interview.javabinterview.thread.juc.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Ju Baoquan
 * Created at  2020/3/9
 */
public class LockSupportDemo {

    /**
     * 测试一: 先唤醒线程，在阻塞线程，线程不会真的阻塞；
     * 测试二: 但是先唤醒线程两次再阻塞两次时就会导致线程真的阻塞
     */
    public static void main(String[] args) throws InterruptedException {
        Thread parkThread = new Thread(new ParkThread());
        parkThread.start();
        for (int i = 0; i < 2; i++) {
            System.out.println("开始线程唤醒");
            LockSupport.unpark(parkThread);
            LockSupport.park(new LockSupportDemo());
            System.out.println("结束线程唤醒");
        }
    }

    public static class ParkThread implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                System.out.println("开始线程阻塞");
                LockSupport.park();
                System.out.println("结束线程阻塞");
            }
        }








        /**
         *  * persistent://platform/odp/staff.invite.register
         *  * persistent://platform/odp/staff.update.mobile
         *  * persistent://platform/im/operationLog.deleteSession
         *  * persistent://platform/odp/staff.update.email
         *  * persistent://platform/im/operationLog.addSession
         *  * persistent://platform/odp/ihr.staff.reset.pwd
         *  persistent://b/daapiservice/alibaba.assetsredeem.message


         * persistent://middle/resumeextend/create.yueliao.jobresume
         * persistent://middle/resumeextend/resume.download
         * persistent://middle/resumeextend/resume.changeStatus
         * persistent://middle/resumeextend/resume.view
         * persistent://middle/resumeextend/label.delete
         * persistent://middle/resumeextend/label.update
         * persistent://middle/resumeextend/comment.add
         * persistent://middle/resumeextend/forward.mail
         * persistent://middle/resumeextend/save.local
         * persistent://middle/resumeextend/recommend.job
         * persistent://middle/resumeextend/resume.join.recycle
         * persistent://middle/resumeextend/resume.recover
         * persistent://middle/resumeextend/resume.delete
         * persistent://middle/resumeextend/exam.add
         * persistent://middle/resumeextend/exam.complete
         * persistent://middle/resumeextend/interview.reject
         * persistent://middle/resumeextend/interview.accept
         * persistent://middle/resumeextend/update.interview.attendType
         * persistent://middle/resumeextend/interview.notification.by.mail
         * persistent://middle/resumeextend/interview.notification.by.sms
         * persistent://middle/resumeextend/resume.move.to.folder
         * persistent://middle/resumeextend/resume.forward.to.applet
         * persistent://middle/resumeextend/applysuccess
         * persistent://platform/im/operationLog.receiveSession
         * persistent://middle/interview/create.resume.interview
         * persistent://middle/interview/create.interview.detail
         * persistent://migrationlog/resume/log
         */

    }
}
