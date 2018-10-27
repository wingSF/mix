#mysql主从配置
* 多个服务器按照mysql
* master库创建用户并授权，用于slave库访问
* 修改master库的配置文件
    * 配置serviceId
    * 配置日志存储位置
    * 要生成操作日志的库
* 修改slave的配置
    * 配置serviceId
    * 配置relay_log的位置
    * 配置操作slave库的用户账号
    * 配置read-only防止误操作（自己测试发现read-only对root用户不生效）
    * 配置replicate_db，即要主从同步的数据库
    * 配置master的host已经日志起始位置
* 检查slave状态
    * slave_io_running
    * slave_sql_running
