package com.interview.javabinterview.design_pattern.pattern_2;

/**
 * synchronized 同步式
 * @auther: chaErGe
 */
public class Single3 {

    private static Single3 single3;

    private Single3() {
    }

    public synchronized static Single3 getInstance() {
        if (null == single3) {
            single3 = new Single3();
        }
        return single3;
    }
    /**
     * 分析:
     * 优点:
     *      同步式和懒汉式中getInstance方法多了一个synchronized 关键字,使用synchronized 保证了线程的同步,
     *      保证同时只有一个进程进入此方法.从而保证了并发安全(即多线程情况下是真正的单例)
     * 缺点:
     *      我们使用synchronized关键字，相当于每个想要进入该方法的获取实例的线程都要阻塞排队，我们仔细思考一下：
     *      需要吗？当实例已经初始化之后，我们还需要做同步控制吗？: 第一次实例化 (必须保证只有一个实例)
     *      后就不需要再进行控制了这对性能的影响是巨大的。是的，我们只需要在实例第一次初始化的时候同步就足够了
     */
}
