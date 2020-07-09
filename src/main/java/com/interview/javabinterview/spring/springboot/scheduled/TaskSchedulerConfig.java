package com.interview.javabinterview.spring.springboot.scheduled;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Configuration
@ComponentScan("com.interview.javabinterview.spring.springboot.scheduled")
@EnableScheduling
public class TaskSchedulerConfig {
}
