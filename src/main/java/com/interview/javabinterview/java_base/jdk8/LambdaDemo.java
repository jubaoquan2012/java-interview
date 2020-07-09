package com.interview.javabinterview.java_base.jdk8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lamdba表达式
 *
 * @author Ju Baoquan
 * Created at  2020/4/16
 */
public class LambdaDemo {

    private void lambda_1() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
    }

    private void lambda_2() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (String a, String b) -> {
                    return b.compareTo(a);
                }
        );
    }

    private void lambda_3() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (a, b) -> b.compareTo(a));
    }

    private void lambda_4() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer.parseInt("1");
            }
        });

        Thread thread1 = new Thread(() -> Integer.parseInt("1"));
    }
}
