package com.interview.javabinterview.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 *
 * 在IOC 容器初始化启动以后,并不会马上就实例化响应的bean,此时容器仅仅拥有所有对象的BeanDefinition(BeanDefinition：是容器依赖某些工具加载的XML配置信息进行解析和分析，并将分析后的信息编组为相应的BeanDefinition)
 * 只有当getBean()调用的时候才是会触发Bean实例化阶段的活动.
 *
 * Spring Bean 的生命周期:
 * getBean()-->createBean-->doCreateBean()然后会有以下几个步骤,是Spring Bean 的生命周期:
 *
 * 1.实例化    Instantiation
 * 2.属性赋值  Populate: 对应构造方法和setter方法的注入
 * 3.初始化   Initialization
 * 4.销毁     Destruction
 *
 * 参考:https://www.jianshu.com/p/1dec08d290c1
 *spring 生命周期最详解: https://blog.csdn.net/qq_23473123/article/details/76610052
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class BeanLifeCycle {

    public static void main(String[] args) {
        method();//测试
        method_1();//bean的生命周期概述
        method_2();//bean的生命周期详解
    }

    private static void method() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        student student = (student) context.getBean("student");
        //Bean的使用
        student.play();
        System.out.println(student);
        //关闭容器
        ((AbstractApplicationContext) context).close();
    }

    /**
     * bean的生命周期
     */
    private static void method_1() {
        /**
         * 实例化-->属性赋值-->初始化-->销毁
         *
         * 分别对应:
         *  AbstractAutowireCapableBeanFactory.doCreateBean(){
         *      1.createBeanInstance() -> 实例化
         *      2.populateBean() -> 属性赋值
         *      3.initializeBean() -> 初始化
         *  }
         *      4.销毁:是在容器关闭时调用的，详见ConfigurableApplicationContext#close()
         *
         */

        /**
         * 还原 doCreateBean()方法:Spring源码都将忽略无关部分
         * protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args)
         *       throws BeanCreationException {
         *    BeanWrapper instanceWrapper = null;
         *    if (instanceWrapper == null) {
         *        // 实例化阶段！
         *       instanceWrapper = createBeanInstance(beanName, mbd, args);
         *    }
         *    Object exposedObject = bean;
         *    try {
         *        // 属性赋值阶段！
         *       populateBean(beanName, mbd, instanceWrapper);
         *        // 初始化阶段！
         *       exposedObject = initializeBean(beanName, exposedObject, mbd);
         *    }
         * }
         */
    }

    private static void method_2() {
        /**
         * 1.Instance
         *      实例化bean对象(通过构造方法或者工厂方法)
         * 2.Populate properties
         *      设置对象属性(setter等)（依赖注入）
         * 3.BeanNameAware.setBeanName()
         *      如果Bean实现了BeanNameAware接口，工厂调用Bean的setBeanName()方法传递Bean的ID。（和下面的一条均属于检查Aware接口）
         * 4.BeanFactoryAware.setBeanFactory()
         *      如果Bean实现了BeanFactoryAware接口，工厂调用setBeanFactory()方法传入工厂自身
         * 5.BeanPostProcessors.postProcessBeforeInitialization()
         *      将Bean实例传递给Bean的前置处理器的postProcessBeforeInitialization(Object bean, String beanname)方法
         * 6.initializingBean.afterProperties()调用Bean的初始化方法
         * 7.Call custom init-method 调用定制的初始化方法
         * 8.BeanPostProcessors.postProcessAfterInitialization()
         *      将Bean实例传递给Bean的后置处理器的postProcessAfterInitialization(Object bean, String beanname)方法
         * 9.Bean Is Ready To Use: Bean 可以使用了
         * -------------------------------------------
         * Container Is ShunDown
         * 容器关闭之前，调用Bean的销毁方法
         * DisposableBean.destroy()
         * Call custom destroy-method 调用定制化的销毁方法
         */
    }
}
