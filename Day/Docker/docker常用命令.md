1. `docker images` 列出本地主机上的镜像。
2. `docker pull` 从Docker Hub中拉取或者更新指定镜像。

3. `docker run`

   -d 标识是让 docker 容器在后台运行。

   -p 标识通知 Docker 将容器内部使用的网络端口映射到我们使用的主机上。

   –name 定义一个容器的名字，如果在执行docker run时没有指定Name，那么deamon会自动生成一个随机数字符串当做UUID。

   -e 设置环境变量，或者覆盖已存在的环境变量。

   例如：docker run –name mysql -p 3306:3306 -e
   MYSQL_ROOT_PASSWORD=password -dmysql/mysql-server:latest

   含义：容器的名字为mysql，将容器的3306端口映射到本机的3306端口，mysql数据库的密码为password
   ，运行的镜像为mysql/mysql-server:latest

4. `docker ps`

   -a 查看已经创建的容器

   -s 查看已经启动的容器

5. `docker start con_name` 启动容器名为con_name的容器。

6. `docker stop con_name` 停止容器名为con_name的容器。

7. `docker rm con_name` 删除容器名为con_name的容器。

8. `docker rmi img_name` 删除镜像名为img_name的镜像。

9. `docker rename old_name new_name` 重命名一个容器