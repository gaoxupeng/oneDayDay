package thread.经典代码;

import java.util.concurrent.*;

/**
 * 使用多线程来进行求和
 */
public class ThreadCount {

    private  int count = 0;

    public static void main(String[] args) {

        System.out.println(getLittleCount(1,10));
        System.out.println(getCound(100,8));
    }

    public class workTask implements Callable{

        @Override
        public Object call() throws Exception {
            return null;
        }
    }

    public static  int getCound(int num,int threadNum){

        if (num == 0 && threadNum ==0){
            return 0;
        }
        double array = num / threadNum;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        //FutureTask futureTask = new FutureTask(thread);
        executorService.execute(()->{
        });
        return  0 ;
    }

    public static  int getLittleCount(int leftNum,int reightNum){

        int count = 0;
        int forNum = reightNum-leftNum;
        for (int i=0;i<forNum+1;i++){
            count = count + leftNum+i;
        }
        return count;
    }

}
