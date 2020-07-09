package com.interview.javabinterview.a_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@SpringBootApplication
public class InterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewApplication.class, args);
        method_1();
    }

    private static void method_1() {

        /**
         * @SpringBootApplication 注解是Spring Boot的核心注解.它是个组合注解:
         *
         *  1.@SpringBootConfiguration 里面是 @Configuration注解
         *
         *  2.@EnableAutoConfiguration
         *     (1).此注解让Spring Boot根据类路径中的jar包依赖为当前项目进行自动配置
         *         例如:添加了 spring-boot-starter-web 依赖,会自动添加Tomcat和Spring MVC的依赖,
         *         那么Spring Boot 会对Tomcat和Spring MVC进行自动配置
         *         又如:添加了spring-boot-starter-data-jpa 依赖,Spring Boot会自动进行JPA相关的配置
         *     (2).属于核心配置:
         *         @Import({
         *                  AutoConfigurationImportSelector.class,
         *                  AutoConfigurationPackage.class})
         *         AutoConfigurationImportSelector使用会SpringFactoriesLoader.loadFactoryNames()方来
         *         扫描具有 META-INF/spring.factories文件的jar包. 源码中有此文件
         *
         *  3.@ComponentScan
         *
         *  扩展: Spring Boot会自动扫描@SpringBootApplication 所在类的同级包,以及下级包里的Bean
         *  (若为JPA项目,还可以扫面注解@Entity的实体类)
         */
    }
}
