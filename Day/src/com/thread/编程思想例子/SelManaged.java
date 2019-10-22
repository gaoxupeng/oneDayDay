package com.thread.编程思想例子;

public class SelManaged implements Runnable{
    private int countDown = 5;
    private Thread t = new Thread(this);
    public SelManaged(){t.start();}

    @Override
    public String toString() {
        return Thread.currentThread().getName()+"("+ countDown +")";
    }

    @Override
    public void run() {
        while (true){
            System.out.println(this);
            if (--countDown ==0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        for (int i =0;i<5;i++){
            new SelManaged();
        }
    }
}
