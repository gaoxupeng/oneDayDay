package thread.并发编程实战.Fixed;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 *    闭锁，允许一个或多个线程等待其他线程中一组操作完成的同步辅助
 */
public class TestHarness {
    public long timeTasks(int nThreads,final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0;i <nThreads;i++){
            Thread t = new Thread(()->{
                try {
                    startGate.await();  //线程阻塞在此处
                    try {
                        task.run();
                    }finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }


    /**
     * 写一个程序，分别实现闭锁和栅栏，int循环累加到10，输出
     */
}
