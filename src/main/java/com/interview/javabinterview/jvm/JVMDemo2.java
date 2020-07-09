package com.interview.javabinterview.jvm;

/**
 * -XX:NewSize=10m
 * -XX:MaxNewSize=10m
 * -XX:InitialHeapSize=20m
 * -XX:MaxHeapSize=20m
 * -XX:SurvivorRatio=8
 * -XX:PretenureSizeThreshold=20m
 * -XX:MaxTenuringThreshold=15
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -Xloggc:gc.log
 */
public class JVMDemo2 {

    public static void main(String[] args) throws InterruptedException {
      Thread.sleep(30000);
      while (true){
          loadData();
      }
    }

    private static void loadData() throws InterruptedException {
        byte[] data = null;
        for (int i = 0;i<50;i++){
            data = new byte[100*1024];
            System.out.println("test");
        }
        data = null;
        Thread.sleep(1000);
    }
}
