https://blog.csdn.net/m0_66128734/article/details/122652569?utm_source=app&app_version=4.21.1

前面的步骤跟这个帖子走到了第一次输入mysql密码为止,初始密码在本机的一个目录下,但是教程没有说明,获取密码要在终端输入cat /var/log/mysqld.log | grep password指令得到第一次的密码后续继续跟这个教程走,但是这个教程的作者是个傻狗,他的指令都是中文标点害死老子了,括号代表可修改参数,我是故意没括上这个单引号,后续这个newpassword用R/oot123代替了,我用的这个密码

## ALTER USER 'root'@'localhost' IDENTIFIED BY '(new password)';

CREATE USER 'root'@'%' IDENTIFIED BY 'R/oot123';

GRANT ALL ON *.* TO 'root'@'%';

ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'R/oot123';

FLUSH PRIVILEGES;

切终端

firewall-cmd --zone=public --add-port=3306/tcp --permanent

firewall-cmd --zone=public --add-port=8080/tcp --permanent

firewall-cmd --reload

以上就完成了配置,最后在编程的时候连接mysql主机地址用的是(linux设备ip地址加上端口号:3306),密码用的是mysql密码









可以使用以下步骤将Windows下已经生成好的数据库拷贝到Linux的MySQL中：

在Windows上使用 mysqldump 命令将数据库导出为 SQL 文件。例如，假设数据库名为 mydatabase，可以使用以下命令导出该数据库：

mysqldump -u root -p mydatabase > mydatabase.sql

上述命令将在当前目录下生成名为 mydatabase.sql 的 SQL 文件。

将导出的 SQL 文件从 Windows 复制到 Linux。可以使用 scp 命令或其他文件传输工具。

在 Linux 上创建一个新的 MySQL 数据库。例如，可以使用以下命令创建名为 mydatabase 的新数据库：

mysql -u root -p -e "CREATE DATABASE mydatabase"

将导出的 SQL 文件导入到 Linux 中的 MySQL 数据库中。例如，可以使用以下命令将 SQL 文件导入到 mydatabase 数据库中：

mysql -u root -p mydatabase < mydatabase.sql

上述命令将从当前目录中的 mydatabase.sql 文件中读取 SQL 语句，并将其应用到 mydatabase 数据库中。这将在 Linux 中创建与 Windows 中相同的数据库结构和数据。

确认数据库已成功导入。可以使用以下命令登录到 MySQL 中，并列出已创建的数据库：

mysql -u root -p
show databases;

如果 mydatabase 在列表中，则说明数据库已成功导入。

