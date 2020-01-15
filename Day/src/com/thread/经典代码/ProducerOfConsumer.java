package com.thread.经典代码;

import java.util.*;

/**
 * 搞懂一些关键词的作用
 * 当有任务，及时消费
 * @param <T>
 */

public class ProducerOfConsumer<T> {

    final private Queue<T> linkedList = new LinkedList<>();  //工作队列
    final private int Max  = 10;   //工作队列中最大值
    public int count = 0;

    //生产代码
    public synchronized void put(T t){
        while (linkedList.size()==Max){
            try {
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        linkedList.add(t);
        System.out.println("此时工作队列中有:"+linkedList.size()+"条");
        ++count;
        this.notifyAll();
    }

    //消费代码
    public synchronized T get(){
        T t = null;
        while (linkedList.size()==0){
            try {
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        t = linkedList.poll();
        System.out.println("消费一条，此时工作队列中有:"+linkedList.size()+"条");
        this.notifyAll();
        return t;
    }
    public static void main(String[] args) {
        ProducerOfConsumer producerOfConsumer = new ProducerOfConsumer();
        //启动消费者线程
        for (int i = 0; i <10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    producerOfConsumer.get();
                }
            }).start();
        }

        //启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    producerOfConsumer.put(j);
                }
            }).start();
        }
    }
}
