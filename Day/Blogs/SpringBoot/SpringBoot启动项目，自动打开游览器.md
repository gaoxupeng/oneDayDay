1. 之前做SSM项目的时候，启动`TOMCAT`的时候，成功是可以直接弹出项目首页的，是 非常的方便，最近在做`SpringBoot`的时候，启动却不弹出项目首页，非常的不方便，所以百度了一番，实现了项目启动，直接弹出首页。

2. `application.properties`文件，添加下方配置。

   ```properties`
   #启动项目时是否自动弹出游览器
   spring.auto.openurl=true
   spring.web.loginurl=http://localhost:9098  //配置项目打开哪个页面
   spring.web.googleexcute=C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe
   ```

3.  创建JAVA配置类。

   ```JAVA
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.boot.CommandLineRunner;
   import org.springframework.stereotype.Component;
   
   @Component
   public class MyCommandRunner implements CommandLineRunner {
       private static Logger logger = LoggerFactory.getLogger(MyCommandRunner.class);
       @Value("${spring.web.loginurl}")
       private String loginUrl;
   
       @Value("${spring.web.googleexcute}")
       private String googleExcutePath;
   
       @Value("${spring.auto.openurl}")
       private boolean isOpen;
   
       @Override
       public void run(String... args) throws Exception {
           if(isOpen){
               String cmd = googleExcutePath +" "+ loginUrl;
               Runtime run = Runtime.getRuntime();
               try{
                   run.exec(cmd);
                   logger.debug("启动浏览器打开项目成功");
               }catch (Exception e){
                   e.printStackTrace();
                   logger.error(e.getMessage());
               }
           }
       }
   }
   ```

4. 完事，可以直接启动项目查看了。