package com.interview.javabinterview.java_base.classinit.deadloop3;

/**
 * @author jianyun.zhao
 * Created at 2019/11/13
 */
public class DeadLoopClass {

    static {
// 如果不加上这个if语句，编译器将提示＂Initializer does not complete normally",并拒绝编译
        if (true) {
            System.out.println(Thread.currentThread() + " init DeadLoopClass");
            while (true) {
            }
        }

    }
}
