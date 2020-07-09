package com.interview.javabinterview.spring.springboot.threadpool.bootimpl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.scheduling.annotation.Async;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    /**
     * 对应{@link ExecutorConfig : 中定义的Bean的name}
     */
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");
    }
}
