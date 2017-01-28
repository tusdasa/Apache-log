	# Apache SSL Error Log 处理程序

一个简单日常处理程序



> 功能：
>
> > 1. 读入日志，并分离出访问IP
> > 2. 统计访问次数
> > 3. 写入数据库



数据库： MySQL

​	请把`lib`中的`mysql-connector-java-5.1.40-bin.jar`导入`jre\lib\ext`中



工程：

`DataBase`   数据链接管理类

`DataBase`  日志处理格式化

`main`  主类



使用：

​     在`config.ini` 配置数据库信息

运行以下`SQL`代码

```sql
CREATE DATABASE apache_log;
CREATE TABLE `https_log` (
  `ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `times` int(11) NOT NULL,
  PRIMARY KEY (`ip`)
);
```



终端：

```shell
java -jar apache.jar 日志
```

