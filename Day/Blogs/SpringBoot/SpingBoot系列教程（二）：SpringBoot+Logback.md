  - 推荐阅读[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging)

  - Sping Boot使用`Commons Logging`来进行所有的内部日志记录，提供了`Java Util Logging`，`Log4J2`和`Logback`的默认配置。

      - 如果使用`Logback`则需要添加如下依赖

        ```css
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
        ```
        而spring-boot-starter-web已经添加了如下依赖，因此没有必要再次添加。![在这里插入图片描述](https://img-blog.csdnimg.cn/20200221221335256.png)

- 当我们启动一个项目，控制台会显示如下图信息

     ![](https://img-blog.csdnimg.cn/2020022122183448.png)
     
    - 输出以下项目：
      - 日期和时间：毫秒精度，易于排序。
      - 日志级别：`ERROR`，`WARN`，`INFO`，`DEBUG`，或`TRACE`。（` Logback`没有`FATAL`级别。它映射到`ERROR`。）
      - 进程ID。
      - 一个`---`分离器来区分实际日志消息的开始。
      - 线程名称：用方括号括起来（对于控制台输出可能会被截断）。
      - 记录器名称：这通常是源类名称（通常缩写）。
      - 日志消息。

#### 日志持久化

- 日志信息只是在控制台打印，如果需要持久化到本地或指定路径，则需要特殊配置。



- 如果代码文件中使用请参照下方代码,`UserController.class`保证了日志输出时，带有这个类的名称，方便后期问题定位。

  ```java
  @RestController
  @RequestMapping("user")
  public class UserController {
  
      @Autowired
      public UserService userService;
  	
      private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
  
      @RequestMapping("getAllUser")
      public List<TbUser> getAllUsers(){
          List<TbUser> userList = userService.list();
          LOG.info("查询到的数据为"+userList);
          return userList;
      }
  ```

  ![](https://img-blog.csdnimg.cn/20200221223553229.png)



#### 日志级别

- 配置级别，参考下方代码

  ```css
  logging.level.root=INFO
  logging.level.com.mall.controller=WARN
  ```

  `logging.level`可指定具体路径下的文件的日志级别，其中`ROOT`指定是所有的文件。

#### 日志格式

- 配置格式，参考下方代码

  ```css
  logging.pattern.console=%d{yyyy/MM/dd-HH:mm:ss} [%thread] %-5level %logger- %msg%n 
  logging.pattern.file=%d{yyyy/MM/dd-HH:mm} [%thread] %-5level %logger- %msg%n
  ```

  - %d{yyyy/MM/dd-HH:mm:ss}   -- 时间格式
  - %thread --线程ID
  - %-5level --日志级别
  - %logger --日志输出类
  - %msg --日志消息
  - %n --文本换行

#### 关于日志的按日期和大小生成文件

- 默认的日志文件是不会自动按天分割，网上冲浪了一番，发现了一个不错的博客，贴在下方，可参考博主的做法。其中，配置文件很详细，也标明了日志的相关配置，包括大小。

  [日志按日期分割](https://blog.csdn.net/qq_33337927/article/details/90902162)

- 注意下不同的环境有着不同日志生成策略，比如本地开发，日志文件直接输出在控制台就行。

- 推荐阅读官方文档，不过是英文，好累。

