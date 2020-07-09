package com.interview.javabinterview.proxy.jdk;

import com.interview.javabinterview.proxy.pojo.Animal;
import com.interview.javabinterview.proxy.pojo.Dog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/7/6
 */
public class JDKProxyDemo {

    public static void main(String[] args) {
        while (true) {
            proxyMethod();
        }
    }

    private static void proxyMethod() {
        Animal target = new Dog();
        // 创建一个代理实例(newProxyInstance)构造三个参数:类加载器、目标类的接口、
        Animal proxyDog = (Animal) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("现在执行的方法都会经过动态代理......JDKProxy");
                        method.invoke(target, args);
                        return null;
                    }
                }
        );

        proxyDog.run();
        proxyDog.eat("狗屎");
    }
}
