# mysql配置调优
* 连接数
    * max_connection
    * linux ulimit -a
    * mysqld.service
* 单个连接
    * sort_buffer_size
    * join_buffer_size
* 全局配置
    * innodb_buffer_pool_size

# 数据库范式
* 单列所存储的数据不可拆分
* 每个记录都必须有一个唯一的主键id
* 表中没有冗余数据