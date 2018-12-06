# mysql语句执行过程
* JDBC是java的一种规范/ODBC是微软公司的一种规范
* connection pool 接收client端发来的请求
    * 管理权限校验，线程处理
    * 此处是半双工的通信方式
    * 常用命令
        * show processlist;
        * show full processlist
        * 重点关注state
* SQL interface
    * 接收用户的sql请求，返回用户查询的结果        
* mysql缓存（Cache And Buffer）
    * 缺陷1：表中任意一个数据的变化，都会引起整个与该表相关的缓存失效
    * 缺陷2：缓存的是sql语句，中间增加空格等字符不会命中缓存
    * 缺陷3：缓存失效算法，不确定是不是可配置的
    * 缺陷4：别的缓存方案，控制程度粒度都优于mysql，所有连mysql自己，默认缓存是关闭的
    * 缺陷5：带函数的查询不会进缓存，查询系统表不会进缓存
    * 缺陷6：查询结果超过query_cache_limit不会进入缓存
    * 缺陷7：当innodb有事务没有提交的时候，所有涉及该表的查询都不会进入缓存
    * 常用命令
        * show variables like '%query_cache%'
        * sql_no_cache|sql_cache
        * show status like 'Qcache%'
* sql解析器（Parser）+sql优化器（Optimizer）
    * 对sql语句进行解析，语法错误等直接返回
    * 对sql进行分析，适当调整，选择最优方式去执行
        * 等价变化  a > b and b = 5   ----->   a > 5
        * count/min/max优化，MyISAM可以直接返回count，索引min/max可以直接查询最左或者最右
        * 覆盖索引，无需回表；当辅助索引+主索引可以覆盖时，也不会回表
        * limit提前终止
        * in 和 or 的区别
        * in后面会有一个集合，优化器会对in集合进行排序，然后采用二分的形式去查找是否命中，O(logN)
        * 多个or连接，只要有一个匹配就会返回
    * explain详细分析
        * id大的执行计划先执行，id相同，顺序执行
        * select_type:查询的性质介绍，个人认为作用不大
        * type从好到坏排序：system > const > eq_ref > ref > range > index > all
            * system表中只有一条记录
            * const，只用一个索引搞定
            * eq_ref,唯一所以搞定
            * ref，非唯一索引
            * range，索引区域
            * index，扫描全部索引
            * ALL，全表扫描
        * possiable_key:可能用到的key
        * key:实际执行过程用到的key
        * rows:读取这么多行，才能找到结果
        * filtered:返回的行占结果的比重，100%为最佳
    * extra
        * using filesort:使用了外部排序，与索引排序不一致
        * using tempory:使用了临时表
        * using index:使用了覆盖索引，无需回表
        * using where:使用了where条件
        * select tables optimized away:// todo 待完善
* pluggable storage engines
    * MyISAM
        * 存有单表的数据量
        * 索引和文件单独存储
        * 主键索引和辅助索引都存储了数据在文件中的地址
        * 不支持事务
    * Innodb
        * 完全支持事务的ACID
        * 行锁
        * 聚集索引
        * 支持外键
    * Memory
        * 内存型的，默认hash索引，支持B+索引
        * 有容量限制，默认16M，不支持大字段 blob，text
        * 临时表会使用
        * 表锁
    * CSV
        * 导入导出很方便
        * 修改数据，只需要flush即可
        * 没索引
        * 不能自增
        * 不能为null
    * Archive
        * 占用数据空间超级小，同样的数据量，占用量相比innodb是百分之几
        * 只支持insert和select
        * 只有自增可以索引
        * 行锁
        * 不支持事务
        
* file System
    * 数据，日志（redo，undo），索引，慢查询
    * 慢查询
        * 查询命令:show variables like '%slow_query_log%'
        * 开启命令:set global slow_query_log = on;
        * 修改日志位置:set global slow_query_log_file = '/usr/local/mysql/data/wing-SF-slow.log'
        * 没有使用索引的日志记录:set global log_queries_not_using_indexes = on
        * 查询超时的记录:set global long_query_time = 0.1(s)
        * 日志分析
            * Time时间
            * User@Host
            * 重要的time
                * Query_time
                * lock_time
                * Rows_sent
                * Rows_examined
            * set timestamp
            * 