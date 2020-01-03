package com.interview.javabinterview.spring_mvc.mvc.action;



import com.interview.javabinterview.spring_mvc.framework.annotation.GPAutowired;
import com.interview.javabinterview.spring_mvc.framework.annotation.GPController;
import com.interview.javabinterview.spring_mvc.framework.annotation.GPRequestMapping;
import com.interview.javabinterview.spring_mvc.framework.annotation.GPRequestParam;
import com.interview.javabinterview.spring_mvc.mvc.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/26
 */

@GPController
@GPRequestMapping("/demo")
public class DemoAction {

    @GPAutowired
    private DemoService demoService;

    @GPRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
                      @GPRequestParam("name") String name){
        String result = demoService.query(name);

        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
