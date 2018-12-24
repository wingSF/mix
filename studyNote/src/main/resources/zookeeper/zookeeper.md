# zookeeper
* zookeeper是一种分布式一致性的解决方案
* 常见作用
    * 数据发布/订阅
    * 负载均衡
    * 命名服务
    * 分布式协调/通知
    * 集群管理
    * leader选举
    * 分布式锁
    * 分布式队列
* zookeeper本身是一个分布式的程序，集群中必须有 半数以上 节点存活，该集群才可用
* zookeeper数据保存在内存中，从而保证低延迟，高吞吐，同时要求节点不能保存大量数据
* zookeeper适用于读多写少的场景，写的时候会涉及多个机器之间进行副本同步
* session
    * 客户端与zookeeper之间开启一个tcp长链接，客户端通过心跳检测与服务端保持有效的会话
    * 同时可以像服务器发送请求，接收响应
    * sessionTimeout的参数，当发生网络问题时，只要在这个时间段内，能够链接到任何一个zookeeper上，会话就依然有效
    * 会话创建的时候都会有一个sessionId，全局唯一            
* 节点
    * 机器节点
        * 广义的zookeeper服务所有的机器
    * ZNode节点
        * 数据模型中的数据单元
    * 数据模型
        * zookeeper将所有数据存储在内存中，数据模型是一棵树，由(/)进行分隔的路径，就是一个ZNode
        * 每个ZNode上面都会保存自己的数据内容，同时会保存一些属性信息
    * 持久节点\临时节点
        * 持久节点只要创建了，就一直存在，除非主动移除
        * 临时节点随着会话的创建而创建，消失的时候该客户端创建的所有临时节点都会被删除
* 特性
    * 顺序一直性:客户端发起的事务请求，最终都会按照发起顺序处理
    * 原子性:集群中的所有机器，要么同时应用了该事务的结果，要么都不变
    * 单一系统镜像:链接到集群中的任何一台机器上，看到的数据模型都一样
    * 可靠性:一旦一次更改被应用，更改的结果都会被持久化，直到下次被修改
* 顺序访问
    * 对于客户端发来的事务请求，zk会分配全局唯一的递增编号zxid，用来反应事务操作的先后顺序
* 集群模型
    * leader/follower/observer
    * leader
        * 事务请求的唯一调度和处理者，保证集群事务处理的顺序性
        * 集群内部各服务器的调度者
    * follower
        * 处理客户端的非事务请求，转发事务请求到leader
        * 参与leader选举
    * observer
        * 不参与leader选举
        * 不参与过半写成功
        > leader提供读写服务，follower/observer提供读服务
* 数据同步
    * 节点间数据同步依赖zab(zookeeper atomic broadcast)协议
    * 当leader节点挂掉之后，zab进入恢复模式
        * 节点投票，超过半数，成为准leader
        * follower提交最近事务，准leader接收提交的事务
        * 准leader同步事务到各个follower，准leader成为真正的leader
        * zab由恢复进入广播，对外提供服务