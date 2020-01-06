package com.thread.经典代码;

import java.util.concurrent.*;

/**
 * 使用多线程来进行求和
 */
public class ThreadCount {
    int value = 0;

    ThreadCount(int value) {
        this.value = value;
    }

    public static class MultiSum implements Runnable {
        private ThreadCount sum;
        private int fromInt;
        private int toInt;
        private int threadNo;

        public MultiSum(ThreadCount sum, int fromInt, int toInt, int threadNo) {
            this.sum = sum;
            this.fromInt = fromInt;
            this.toInt = toInt;
            this.threadNo = threadNo;

        }

        @Override
        public void run() {
            long current = System.currentTimeMillis();
            for (int i = fromInt; i <= toInt; i++) {
                this.sum.value += i;
            }
            current = System.currentTimeMillis() - current;
            System.out.println("Thread." + threadNo + " executes sum from "
                    + fromInt + " to " + toInt + " in " + current
                    + " milseconds. Sum is " + sum.value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Integer toMax = 20000; // 对从1到20,000进行加和
        Integer sumInteger = 0;
        int threads = 8; // 计算线程数
        // 每个线程计算一段连续的加和，并将加和结果保存在数组中。
        ThreadCount[] subSum = new ThreadCount[threads];
        for (int i = 0; i < threads; i++) {
            subSum[i] = new ThreadCount(0);
        }
        for (int i = 0; i < threads; i++) {
            int fromInt = toMax * i / threads + 1; // 边界条件
            int toInt = toMax * (i + 1) / threads; // 边界条件
            new Thread(new MultiSum(subSum[i], fromInt, toInt, i)).start();
        }
        Thread.sleep(20);
        for (int i = 0; i < threads; i++) {
            sumInteger += subSum[i].value;
        }
        System.out.println("The sum is :" + sumInteger);
    }
}
