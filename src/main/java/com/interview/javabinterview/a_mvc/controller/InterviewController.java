package com.interview.javabinterview.a_mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@RestController
public class InterviewController {

    @RequestMapping("/")
    String index() {
        return "Hello Spring Boot";
    }

    // http://localhost:8080/say?name=jubaoquan
    @RequestMapping("/say")
    String say(String name) {
        return "Hello Spring Boot:" + name;
    }
}
