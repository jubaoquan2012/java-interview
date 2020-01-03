package com.interview.javabinterview.java_base.final_static;

/**
 * 说明
 *
 * @author: Ju Baoquan
 * Created at:  2019/4/9
 */

public class WithFinalFields {

    static final SelfCounter wffs = new SelfCounter();

    final SelfCounter wff = new SelfCounter();

     final int a ;

    public WithFinalFields(){
        a =1;
    }

    public String toString() {
        return "static final:wffs= " + wffs + "--- final:wff= " + wff;
    }

//    public String toString() {
//        return "static final:wff= " + wff ;
//    }

//    public String toString() {
//        return "final:wff= " + wffs;
//    }
}
