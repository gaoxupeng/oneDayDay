package com.thread.Demo;

public class SleepAndWait {

    public static void main(String[] args) {

        Thread thread = new Thread();
        thread.setName("线程1");
        thread.start();

        int count = 10;
        new Thread(()->{
            while (count == 5){
                try {
                    new SleepAndWait().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
        }).start();

    }
}
