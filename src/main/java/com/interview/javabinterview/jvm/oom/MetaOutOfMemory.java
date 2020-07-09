package com.interview.javabinterview.jvm.oom;

import com.interview.javabinterview.proxy.pojo.Car;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 演示Meta区域OOM
 *
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:MetaspaceSize=10m
 * -XX:MaxMetaspaceSize=10m
 * -XX:+PrintGCDetails
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/data
 * -Xloggc:gc.log
 *
 * @author Ju Baoquan
 * Created at  2020/7/8
 */
public class MetaOutOfMemory {

    public static void main(String[] args) {
        while (true){
            proxyMethod();
        }
    }
    private static void proxyMethod() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if (method.getName().equals("run")) {
                    System.out.println("启动汽车之前,先进行自动的安全检查");
                    return methodProxy.invokeSuper(o, objects);
                } else {
                    return methodProxy.invokeSuper(o, objects);
                }
            }
        });

        Car car = (Car) enhancer.create();
        car.run();
    }
}
