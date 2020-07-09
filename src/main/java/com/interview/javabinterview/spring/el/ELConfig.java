package com.interview.javabinterview.spring.el;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Configuration
@ComponentScan("com.interview.javabinterview.spring.el")
@PropertySource("classpath:application-el.properties")
public class ELConfig {

    @Value("I Love You!")
    private String normal;

    @Value("#{systemProperties['os.name']}")
    private String osName;

    @Value("#{T(java.lang.Math).random() * 100.0}")
    private double randomNumber;

    @Value("#{springELService.another}")
    private String fromAnother;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void outputResource() {
        System.out.println(normal);
        System.out.println(osName);
        System.out.println(randomNumber);
        System.out.println(fromAnother);
        System.out.println(bookName);
        System.out.println(environment.getProperty("book.author"));
    }
}
