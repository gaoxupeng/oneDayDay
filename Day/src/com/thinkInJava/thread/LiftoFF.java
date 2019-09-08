package com.thinkInJava.thread;

public class LiftoFF implements Runnable{

    protected int countDown = 10;
    private static int tackCount = 0;
    private final int id = tackCount++;

    public LiftoFF() {
    }

    public LiftoFF(int countDown) {
        this.countDown = countDown;
    }

    public String status(){
        return "#"+ id + "(" + (countDown >0 ? countDown : "LiftoFF!") + "),";
    }


    @Override
    public void run() {
        while (countDown --> 0){
            System.out.println(status());
            Thread.yield(); //对静态方法的调用是对线程调度器的一种声明。
        }
    }
}
