# [mybatis](http://www.mybatis.org/mybatis-3/zh/index.html)
## What is mybatis
> Mybatis is a first class persistence framework with support for custom sql / stored procedures / advanced mappings.  
Mybatis eliminates almost all of the jdbc code / manual setting of parameters / retrieval of result  
Mybatis can use simple XML or Annotations for configuration and map primitives / map interfaces / java pojos to database records
* 核心对象
    * SqlSessionFactoryBuilder
    * SqlSessionFactory
    * SqlSession
    * mapper instances(映射器)
    * TypeHandler 
    * ObjectFactory
    * Interceptor
    * DataSourceFactory
    * DatabaseIdProvider
    * Configuration
    * SQL-> AbstractSQL
    
# SqlSessionFactoryBuilder
* 核心方法
    * build(Configuration config)
        * build(Reader reader, String environment, Properties properties)
        * build(InputStream inputStream, String environment, Properties properties)
        * 以上俩个方法都是通过xPath解析xml标签，封装到configuration对象中
        * [todo]() environment Properties Configuration
        > 用'>'暂时表示包含关系，来确定下面分析的顺序  
        Configuration > Environment > TransactionFactory & DataSource
    * DataSource
        * jdk rt.jar javax.sql.DataSource
        * extends CommonDataSource,Wrapper
            * CommonDataSource
                * 子接口 DataSource,XADataSource,ConnectionPoolDataSource
                    * 子接口定义的都是获取Connection的方法
                    > Connection PooledConnection XAConnection
                * 作用:定义三种子接口的相同方法
                * 方法介绍
                    * set/get LogWriter 日志相关
                    * set/get LoginTimeout 登录超时相关
                    * getParentLogger 获取最顶层Logger对象
            * Wrapper
                * unwrap 获取某个接口的实现类或者代理
                * isWrapperFor  判断是否是某个接口的实现类
                > The wrapper pattern is employed by many JDBC driver implementations to provide extensions beyond the traditional JDBC API that are specific to a data source
    * TransactionFactory
        * 作用：创建事务
        * 方法
            * setProperties 设置事务工厂的基本配置
            * newTransaction(Connection) 通过一个已经实例化好的conn对象创建事务
            * newTransaction(DataSource,TransactionIsolationLevel,autoCommit) 通过DataSource创建事务
    * Environment
        * build(id,TransactionFactory,DataSource) 核心方法
        * 内部一个build类，感觉很鸡肋，想不到好词来赞扬他
    * LocalCacheScope
        * 指定缓存的作用域
        * SESSION,STATEMENT 枚举值
        * [todo]() 找出哪里使用了，如何保证的逻辑
    * JdbcType
        * 数据库数据类型与java数据类型的映射关系
    * ExecutorType
        * SIMPLE 就是普通的执行器
        * REUSE 执行器会重用预处理语句（prepared statements)
        * BATCH 执行器将重用语句并执行批量更新
    * AutoMappingBehavior
        * NONE 表示取消自动映射
        * PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集
        * FULL 会自动映射任意复杂的结果集（无论是否嵌套)
    * AutoMappingUnknownColumnBehavior
        * NONE: 不做任何反应
        * WARNING: 输出提醒日志 ('org.apache.ibatis.session.AutoMappingUnknownColumnBehavior' 的日志等级必须设置为 WARN)
        * FAILING: 映射失败 (抛出 SqlSessionException)
    * ReflectorFactory
        * impl
            * DefaultReflectorFactory
        * 成员变量
            * ConcurrentMap<Class<?>, Reflector>
                * 
    * SqlSessionFactory
        * 用于创建SqlSession
        * 可以返回当前的Configuration配置
        * impl
            * DefaultSqlSessionFactory
            * SqlSessionManager
            > DefaultSqlSessionFactory只实现了SqlSessionFactory  
            SqlSessionManager实现了SqlSessionFactory和SqlSession
    * SqlSession
        * 可以执行命令
        * 获取mapper
        * 管理事务
        * impl
            * DefaultSqlSession
        