package thread.并发编程实战.Six;

import sun.nio.ch.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 推荐自定义线程池
 * FixedThreadPool 和 SingleThreadPool（俩个都是无界队列） 容易堆积大量请求，从而导致oom
 * CachedThreadPool 和 ScheduledThreadPool 容易创建大量线程，从而导致oom
 * 为什么？
 */
public class TaskExecutionWebServer {
    private static final int num = 100;
   // public static final Executor exec = Executors.newFixedThreadPool(num);
   public static final ExecutorService exec = new ThreadPoolExecutor(5,10,10,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(5));

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);

        while (true){
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    //一些业务操作
                }
            };
            exec.execute((Runnable) connection);
        }
    }
}
