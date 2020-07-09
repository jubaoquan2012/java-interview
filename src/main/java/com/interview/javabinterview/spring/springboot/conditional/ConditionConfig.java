package com.interview.javabinterview.spring.springboot.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Configuration
public class ConditionConfig {

    @Bean
    @Conditional(WindowCondition.class)
    public ListService windowsListService() {
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxListService() {
        return new LinuxListService();
    }
}
