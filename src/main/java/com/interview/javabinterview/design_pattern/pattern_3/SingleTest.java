package com.interview.javabinterview.design_pattern.pattern_3;


import java.util.HashSet;
import java.util.Set;


/**
 * @auther: chaErGe
 */
public class SingleTest {

    public void test1() throws InterruptedException {
        Set<Single1> set =  new HashSet<>();
        for(int i = 0;i < 2000; i++){
            new Thread(() ->{
                set.add(Single1.getInstance());
            }).start();
        }
        Thread.sleep(10000);
        System.out.println("测试Single单例模式");
        for(Single1 single : set){
            System.out.println(single);
        }
    }
}
