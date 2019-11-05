package com.thread.并发容器;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentMap {
    public static void main(String[] args) {

       // Map<String,String> map = new ConcurrentHashMap<>();
        Map<String, String> map = new ConcurrentSkipListMap<>(); //高并发并且排序
        Random random = new Random();
        Thread[] threads = new Thread[100];

        CountDownLatch latch = new CountDownLatch(threads.length);
        long start = System.currentTimeMillis();

        for (int i = 0;i<threads.length;i++){
            threads[i] = new Thread(()->{
                for (int j=0;j<10000;j++){
                    map.put("a"+random.nextInt(10000),"a"+random.nextInt(10000));
                }
                latch.countDown();
            });
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
        Arrays.asList(threads).forEach(thread->thread.start());
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
