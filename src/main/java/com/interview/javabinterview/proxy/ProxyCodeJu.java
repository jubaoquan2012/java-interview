package com.interview.javabinterview.proxy;

import com.interview.javabinterview.proxy.pojo.Animal;
import com.interview.javabinterview.proxy.pojo.Dog;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/7/6
 */
public class ProxyCodeJu {

    /**
     * 代理有两种:
     * 静态代理:
     * 代理类->目标类
     * 动态代理:
     * 代理类->中间类->目标类.
     * <p>
     * JDKProxy:
     * CGLIBProxy:
     */
    public static void main(String[] args) {
        //JDKProxy demo 演示
        method_1();
        //CGLIBProxy demo 演示
        method_2();
    }

    private static void method_1() {
        // 生成proxy0的class文件,也就是代理类的字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);
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

    private static void method_2() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("现在执行的方法都会经过动态代理......CGLIBProxy");
                return methodProxy.invokeSuper(o, objects);
            }
        });

        Dog proxyDog = (Dog) enhancer.create();

        proxyDog.run();
        proxyDog.eat("狗屎");
    }
}
