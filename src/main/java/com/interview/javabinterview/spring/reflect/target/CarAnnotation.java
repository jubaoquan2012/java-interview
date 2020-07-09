package com.interview.javabinterview.spring.reflect.target;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author Ju Baoquan
 * Created at  2020/5/22
 * @Target: 自定义注解的使用范围
 * ElementType.METHOD：方法声明
 * ElementType.TYPE：类、接口（包括注解类型）或enum声明
 * ElementType.CONSTRUCTOR：构造器的声明
 * ElementType.FIELD：域声明（包括enum实例）
 * ElementType.LOCAL_VARIABLE：局部变量声明
 * ElementType.PACKAGE：包声明
 * ElementType.PARAMETER：参数声明
 * @Retention 注解级别信息
 * RetentionPolicy.RUNTIME：VM运行期间保留注解，可以通过反射机制读取注解信息
 * RetentionPolicy.SOURCE：注解将被编译器丢弃
 * RetentionPolicy.CLASS：注解在class文件中可用，但会被VM丢弃
 * @Documented 将注解包含在Javadoc中
 * @Inherited 允许子类继承父类中的注解，默认不能被子类继承
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CarAnnotation {

    String version() default "默认 2000年款";//default默认值

    int price() default 20000;

    String[] color() default {"红", "黄", "蓝"};
}
