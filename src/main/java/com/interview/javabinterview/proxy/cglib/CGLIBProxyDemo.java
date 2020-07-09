package com.interview.javabinterview.proxy.cglib;

import com.interview.javabinterview.proxy.pojo.Car;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIBProxy 演示在Meta区创建对象, 可能会导致OOM
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
 *
 * @author Ju Baoquan
 * Created at  2020/7/6
 * link:https://www.cnblogs.com/wyq1995/p/10945034.html
 *
 */
public class CGLIBProxyDemo {

    public static void main(String[] args) {
        while (true) {
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