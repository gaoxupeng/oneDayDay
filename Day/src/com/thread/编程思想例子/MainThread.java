package com.thread.编程思想例子;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread {
    public static void main(String[] args) {
//        LiftoFF liftoFF = new LiftoFF();
//        liftoFF.run();

//        for (int i=0;i<5;i++){
//            new Thread(new LiftoFF()).start();

        ExecutorService executors = Executors.newCachedThreadPool();

        for (int i=0;i<5;i++) {
            executors.execute(new LiftoFF());
            executors.shutdown();
        }
    }
}
