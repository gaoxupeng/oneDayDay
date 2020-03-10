- 近期，购买了个阿里云服务器，因此一些数据库就放到了云服务器上，但是因为是GitHub项目，为了安全起见，本地装个Nginx，来反向代理数据库连接。
- 本次环境是Windows，但大同小异，修改配置文件即可。
- 注意Nginx的版本，此次使用了Nginx的stream特性。这个特性应该是1.14.2版本新增的。我使用的是1.17.9版本。

[关于Windows下Nginx版本无缝升级的博客](https://blog.csdn.net/u010178308/article/details/84901001)

1. 打开Nginx的配置文件nginx.conf，添加如下标签，与http同级

   ```
   stream {
   	#mysql
       server {
           listen  4000;  //本地请求端口
           proxy_pass ***.***.***.***:***;  //转发到服务器的地址
       }
   	#redis
   	server {
           listen  4001;
           proxy_pass ***.***.***.***:***;
       }
   	#mongdb
   	server {
           listen  4002;
           proxy_pass ***.***.***.***:***;
       }
   }
   ```

   然后重启即可，可以使用一些数据库连接工具来测试下。可以设置一些超时参数，如proxy_timeout，具体请查看官方文档，自行百度。

