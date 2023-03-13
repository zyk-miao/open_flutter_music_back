[open_flutter_music_back](https://github.com/zyk-miao/open_flutter_music_back)的后端项目，使用了springboot框架,minio作为对象存储。

使用：
配置mysql数据库，运行sql目录下的music.sql,修改application.yml的数据库配置。
配置minio服务端，minio需要创建名为"music"的桶，以及配置桶Access Rules(prefix填*,access选readonly)，修改application.yml的minio配置,url是host+端口(例如:127.0.0.1:9000,example.com:9000),access是minio用户名,secret是minio的密码。
