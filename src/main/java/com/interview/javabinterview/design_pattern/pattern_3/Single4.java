package com.interview.javabinterview.design_pattern.pattern_2;

/**
 * 双重检验锁
 * @auther: chaErGe
 */
public class Single4 {

    private static volatile Single4 single;

    private Single4(){
    }

    public static Single4 getInstance(){
        //多线程直接访问不需要控制,并且不会影响性能
        if (null == single){
            //此时,如果与多个线程进入,则进入同步快,其余线程等待
            //采用类锁
            synchronized (Single4.class){
                if(null == single){
                    //第一个线程进入single 为空, 创建此实例
                    single = new Single4();
                }
            }
        }
        return single;
    }
    /**
     * 分析:
     *  步骤: 1.先判断是否已经实例化,如果没有实例化就第一次初始化(涉及到线程安全问题),
     *       2.多线程抢占,只有一个进行实例化,先判断是否已经实例化,如果没有就实例化
     *   优点:
     *      和synchronized的单利模式相比, 不用每次获取单例都需要被同步而这个只有第一次的时候进行同步
     *   volatile:
     *   Java虚拟机初始化一个对象都干了些什么-->3件事情：
     *      1.在堆空间分配内存 2.执行构造方法进行初始化 3.将对象指向内存中分配的内存空间，也就是地址.
     *    由于当我们编译的时候，编译器在生成汇编代码的时候会对流程进行优化（这里涉及到happen-before原则和Java内存模型
     *    和CPU流水线执行的知识，就不展开讲了），优化的结果式有可能式123顺序执行，也有可能式132执行，但是，如果
     *    是按照132的顺序执行，走到第三步（还没到第二步）的时候，这时突然另一个线程来访问，走到if(single4 == null)块，
     *    会发现single4已经不是null了，就直接返回了，但是此时对象还没有完成初始化，如果另一个线程对实例 的某些需要初始化
     *    的参数进行操作，就有可能报错。使用volatile关键字，能够告诉编译器不要对代码进行重排序的优化。就不会出现这种问题了。
     *
     */
}
