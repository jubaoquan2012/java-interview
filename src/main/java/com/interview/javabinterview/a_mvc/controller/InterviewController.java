package com.interview.javabinterview.a_mvc.controller;

import com.interview.javabinterview.a_mvc.autoconfig.AutoInterviewConfig;
import com.interview.javabinterview.a_mvc.autoconfig.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@RestController
public class InterviewController {

    @Autowired
    private AutoInterviewConfig config;

    @Autowired
    private CustomConfig customConfig;

    @RequestMapping("/")
    public String index() {
        return "Hello Spring Boot";
    }

    // http://localhost:8080/say?name=jubaoquan
    @RequestMapping("/say")
    public String say(String name) {
        return "Hello Spring Boot:" + name;
    }

    @RequestMapping("/getConfig")
    public Map getConfig() {
        Map<Object, Object> configMap = new HashMap<>();
        configMap.put("name", config.getName());
        configMap.put("book", config.getBook());
        configMap.put("age", config.getAge());
        configMap.put("topic",customConfig.getTopic());
        configMap.put("consuemrId",customConfig.getConsumerId());
        return configMap;
    }
}
