package com.thread.经典代码;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏：所有线程必须同时到达栅栏位置，才能继续执行。  闭锁是为了 等待事件。闭锁是一次性对象。
 * 所有线程都到达了栅栏位置后，就会打开栅栏，所有线程被释放。栅栏被重置，以便下次使用。
 */
public class BarrierTest {
    final int n;
    final double[][] data;
    final CyclicBarrier barrier;
    private final int m;
    volatile int col; // 处理列

    class Work implements Runnable{

        int myRow;

        public Work(int myRow) {
            this.myRow = myRow;
        }

        @Override
        public void run() {
            while (done()){
                processRow(myRow);
                System.out.println("handle row"+myRow+"over");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean done() {
        return col<m;
    }

    private void processRow(int myRow) {
        data[myRow][col]=Math.random();
        System.out.println("row"+myRow+""+Arrays.toString(data[myRow]));
    }

    public BarrierTest(double[][] data) {
        this.n = data.length-1; // 要生产的计算总行数
        this.m =data[0].length;// 总列数 
        this.col=0;
        this.data = data;
        this.barrier = new CyclicBarrier(this.n, new Runnable() {
            @Override
            public void run() {
                mergeRows();
            }
        });

        for (int i = 0; i <this.n ; i++) {
            new Thread(new Work(i)).start();
        }
    }

    private void mergeRows() {
        System.out.println("开始合计第"+col+"列");
        // 合并计算第col列的总和
        double tmp=0.00;
        for (int i = 0; i <this.n ; i++) {
            tmp+=data[i][col];
        }
        data[this.n][col]=tmp;
        for (int i = 0; i <=this.n ; i++) {
            System.out.println(Arrays.toString(data[i]));
        }
        System.out.println("完成合计第"+col+"列");
        this.col++;
    }

    public static void main(String[] args) {
        new BarrierTest(new double[5][7]);
    }


}