package com.interview.javabinterview.java_base.final_static;

/**
 * 说明
 *
 * @author: Ju Baoquan
 * Created at:  2019/4/9
 */

public class SelfCounter {

    private static int counter;

    private int id = counter++;

    private final int ids = counter++;

    public String toString(){
        return "SelfCounter:id: "+id+"--- counter:"+counter+":---ids:"+ids;
    }
}
