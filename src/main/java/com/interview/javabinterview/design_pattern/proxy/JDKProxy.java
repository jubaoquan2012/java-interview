package com.interview.javabinterview.design_pattern.proxy;




import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/16
 */
public class JDKProxy implements InvocationHandler {

    private Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        System.out.println(declaringClass);
        if (Object.class.equals(method.getDeclaringClass())){//MapperProxy中就用到了
            System.out.println("object的方法才会走:"+method.getName());
        } else if(isDefaultMethod(method)){
            System.out.println("非Object的方法:"+method.getName());
            method.invoke(target,args);
        }
        System.out.println("invoke__run: before realSubject");
        return null;
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) {
        return null;
    }

    private boolean isDefaultMethod(Method method) {
        return true;
    }
}
