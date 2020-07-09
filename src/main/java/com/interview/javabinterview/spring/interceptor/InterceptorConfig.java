package com.interview.javabinterview.spring.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private InterceptorRegistry registry;

    @Bean
    public DemoExtendsInterceptor demoExtendsInterceptor() {
        return new DemoExtendsInterceptor();
    }

    @Bean
    public DemoImplementsInterceptor demoImplementsInterceptor() {
        return new DemoImplementsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        this.registry = registry;
    }

    public void addInterceptors(HandlerInterceptor interceptor) {
        registry.addInterceptor(interceptor);
    }
}
