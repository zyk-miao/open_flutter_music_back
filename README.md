[open_flutter_music](https://github.com/zyk-miao/open_flutter_music)的后端项目，使用了springboot框架,minio作为对象存储。

配置mysql数据库，运行sql目录下的music.sql,修改application.yml的数据库配置。

配置minio服务端，minio需要创建名为"music"的桶，以及配置桶策略为public，修改application.yml的minio配置,url是host+端口(例如:127.0.0.1:9000,example.com:9000),access是minio用户名,secret是minio的密码。

# docker-compose部署运行

1. 自行安装docker以及docker-compose

2. 上传depoy目录

3. 在depoy目录下运行`docker-compose up -d`(根据自我需求更改docker-compose.yml文件，如数据库密码，对象存储用户名密码，以及服务端口的映射)

4. 访问minio后台(默认是IP:9001),配置bucket的policy为public

![](https://github.com/zyk-miao/open_flutter_music_back/blob/master/img/public.png)