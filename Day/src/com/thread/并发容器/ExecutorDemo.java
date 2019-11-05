package com.thread.并发容器;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorDemo implements Runnable{
    long start = System.currentTimeMillis();
    Map<String, String> map = new ConcurrentSkipListMap<>(); //高并发并且排序
    Random random = new Random();

    @Override
    public void run() {
            for (int j=0;j<10000;j++){
                map.put("a"+random.nextInt(10000),"a"+random.nextInt(10000));
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.execute(new ExecutorDemo());
        executorService.shutdown();

    }


}
