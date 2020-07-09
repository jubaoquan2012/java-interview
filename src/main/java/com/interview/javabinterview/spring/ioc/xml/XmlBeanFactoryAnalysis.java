package com.interview.javabinterview.spring.ioc.xml;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 编程式使用IOC
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class XmlBeanFactoryAnalysis {

    /**
     * 用xmlBeanFactory初始化
     */
    public static void initBeanByXmlBeanFactory() {
        XmlBeanFactory xmlBeanFactory =
                new XmlBeanFactory(new ClassPathResource("application.xml"));
    }

    /**
     * 用户自定义初始化. 和xmlBeanFactory初始化是完全一样的
     */
    public void initBeanBy() {
        //1.加载文件
        ClassPathResource resource = new ClassPathResource("application.xml");
        //2.创建Bean工厂
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        //3.创建载入BeanDefinition 的读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        //4.读入配置然后,载入和注册到Ioc
        reader.loadBeanDefinitions(resource);
    }
}
