package com.interview.javabinterview.design_pattern.pattern_2;

/**
 * 反射和反序列化破坏单例
 * @auther: chaErGe
 */
public class Single6 {
    private static Single6 single;

    private Single6(){}

    public static Single6 getInstance(){
        return InnerClass.single;
    }

    private static class InnerClass{
        private static final Single6 single = new Single6();
    }

    /**
     * 重写此方法,防止反序列化好破坏单例机制,这是因为:反序列号的机制在反序列号的时候,会判断如果实现了serializable
     * 或者externalizable 接口的类中包含readResolve方法的话,会直接调用readResolve 方法来获取实例
     * @return
     */
    public Object readResolve(){
        return InnerClass.single;
    }

    /**
     * 分析:
     *  我们重写了readResolve方法，在该方法中直接返回了我们的内部类实例。
     *  重写readResolve() 方法，防止反序列化破坏单例机制，这是因为：反序列化的机制在反序列化的时候，
     *  会判断如果实现了serializable或者externalizable接口的类中包含readResolve方法的话，
     *  会直接调用readResolve方法来获取实例。这样我们就制止了反序列化破坏我们的单例模式
     */
}
