package com.interview.javabinterview.spring.springboot.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Service
public class ScheduledTaskService {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("每隔五秒执行一次" + simpleDateFormat.format(new Date()));
    }

    /** 每天下午5点半执行 */
    @Scheduled(cron = "0 30 17 ? * *")
    public void fixTimeExecution() {
        System.out.println("在指定时间" + simpleDateFormat.format(new Date()) + "执行");
    }
}
