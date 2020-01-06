package com.thread.并发编程实战.Eight;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

/**
 * 从此信号量获取许可证，阻止直到可用，否则线程为interrupted 。
 * 获得许可证，如果有可用并立即返回，则将可用许可证数量减少一个。
 *
 * 如果没有可用的许可证，那么当前线程将被禁用以进行线程调度，并且处于休眠状态，直至发生两件事情之一：
 *
 * 一些其他线程调用此信号量的release()方法，当前线程旁边将分配一个许可证; 要么
 * 一些其他线程interrupts当前线程。
 * 如果当前线程：
 *
 * 在进入该方法时设置了中断状态; 要么
 * 是interrupted等候许可证，
 * 然后InterruptedException被关上，当前线程的中断状态被清除。
 */
public class BoundedExecutor {
    private final Executor executor;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
        //System.identityHashCode()
    }

    public void submitTask(final Runnable commad) throws InterruptedException{
        semaphore.acquire();
        try {
            executor.execute(()->{
                try {
                    commad.run();
                }finally {
                    semaphore.release();
                }
            });
        }catch (Exception e){
            semaphore.release();
        }
    }

}
