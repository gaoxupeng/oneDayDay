- 与上篇日志系统相辅相成的就是异常了，这边博客主要讲述如何配置全局异常/自定义异常。

### 相关注解

1. `ControllerAdvice`：用来实现全局异常，需要定义类，添加该注解即可。处理的是`Controller`层异常。

   示例代码：

    ```java
    @ControllerAdvice
    public class ErrorController {
    }
    ```
   
   因为异常处理时候很多使用`JSON`来进行处理，所以很多时候`ControllerAdvice`替换为`RestControllerAdvice`

2. `ExceptionHandler`：指定异常的处理方式，声明于方法的上部。

   实例代码：

   ```java
   //value可设定处理指定异常的类型， Exception为处理所有
   @ExceptionHandler(value = Exception.class)
       Object handleException(Exception e, HttpServletRequest request){
           return object;
       }
   ```

### 代码演示

   - `Controller`部分

     ```java
     @RestController
     @RequestMapping("user")
     public class UserController {
         
         @RequestMapping("error")
         public void error(){
             int i = 1/0;
         }
     
         @RequestMapping("nullException")
         public void nullException(){
            String str = null;
            str.indexOf(",");
         }
     }
     ```

   - `异常处理类`

     ```java
     @RestControllerAdvice
     public class ErrorController {
     
     	//发生异常，写入日志，方便后期查看。
         private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);
     	/**
     	* 默认处理所有异常
     	*/
         @ExceptionHandler(value = Exception.class)
         Object handleException(Exception e, HttpServletRequest request){
             //此处是我的日志格式。
             LOG.error("url{},msg{}",request.getRequestURI(),e.getMessage());
             //定义map，返回异常信息方便操作
             Map<String,Object> map = new HashMap<>();
             //code参数，我理解为状态码，这里自由定义。
             //如果不想使用map，使用实体类等都是可行的。
             map.put("code",100);
             //msg，可自由定义。比如map.put("msg","请求参数错误！");
             map.put("msg",e.getMessage());
             return map;
         }
     
     }
     ```

   - `示例`

     ![](https://img-blog.csdnimg.cn/20200226221756107.png)

   ![](https://img-blog.csdnimg.cn/20200226221840160.png)

   ​	可以看到页面输出了俩种异常，存储在`map`的信息，不过并不直观，且无法准确描述具体的异常。

### 异常拆分

   - 一个方法处理所有的异常，是不妥的。建议拆分多个方法，每个方法对应特定异常，回显特定信息，方便后期的问题定位。当然，下方这种代码，理论上可以区分，但方法太长，不够精简。（想象一个方法一个类的情况）

     ```java
        @ExceptionHandler(value = Exception.class)
             Object handleException(Exception e, HttpServletRequest request){
                 Map<String,Object> map = new HashMap<>();
                     if (e instanceof ArithmeticException) {
                         map.put("code",100);
                         map.put("msg","请求参数错误");
                     } else if (e instanceof NullPointerException) {
                         map.put("code",101);
                         map.put("msg","空指针异常！");
                     }
                     return map;
                 }
     ```

   - 个人建议下方这种

     ```java
      //默认为所有异常，但优先级低于下方方法，因此有NullPointerException异常，执行下方方法，返回信息
      @ExceptionHandler(value = Exception.class)
         Object handleException(Exception e, HttpServletRequest request){
             //此处是我的日志格式。
             LOG.error("url{},msg{}",request.getRequestURI(),e.getMessage());
             //定义map，返回异常信息方便操作
             Map<String,Object> map = new HashMap<>();
             map.put("code",100);
             map.put("msg","后台发生错误");
             return map;
         }
         //处理空指针异常
          @ExceptionHandler(value = NullPointerException.class)
         Object handleNullPointerException(Exception e, HttpServletRequest request){
             LOG.error("url{},msg{}",request.getRequestURI(),e.getMessage());
             Map<String,Object> map = new HashMap<>();
             map.put("code",100);
             //map.put("msg",e.getMessage());
             map.put("msg","空指针异常");
             return map;
         }
     ```


## 后期完善 ##

1. 关于异常再完善下，自定义错误页面相关知识。
2. 异常依赖于`Spring`的`AOP`相关部分，出一篇`AOP`的博客。