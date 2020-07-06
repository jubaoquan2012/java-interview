package com.interview.javabinterview.dubbo.anno.client.configuration;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.interview.javabinterview.dubbo.anno")
@ComponentScan(value = {"com.interview.javabinterview.dubbo.anno"})
public class ClientConfiguration {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("application-dubbo-consumer.xml");
        return applicationConfig;
    }

    @Bean
    public ConsumerConfig providerConfig(){
        return new ConsumerConfig();
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("localhost");
        registryConfig.setPort(2181);
        return registryConfig;
    }

}
