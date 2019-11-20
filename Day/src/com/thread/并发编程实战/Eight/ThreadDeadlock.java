package thread.并发编程实战.Eight;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            Future<String> header,footer;
            header = executorService.submit(new LoadFileTask("head.html"));
            footer = executorService.submit(new LoadFileTask("footer.html"));
            String page = renderBody;
            return header.get()+page+footer.get();  //线程饥饿死锁
        }
    }
}
