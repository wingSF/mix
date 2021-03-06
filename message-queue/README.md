# 消息队列
## 消息队列的作用
* 广义
    * 异步
        * 系统内代码逻辑异步处理，减少单词调用的耗时
    * 解耦
        * 业务之间解耦合
    * 削峰
        * 通过将瞬时的大量请求放到消息队列中，控制出口的速度，实现削峰
* 实际作用
    * 分布式事务的最终以执行保证
    * 通过producer和consumer双向通信模拟实现rpc调用
    
* 为什么使用消息队列
    * 核心服务的数据变更通知别的服务，原始的做法是代码中主动调用别的服务，改为消息队列订阅方式，业务解耦，由数据的接收方来做可靠性保证。
    * 核心接口的响应时间有一定要求，把强一致性的业务放到当前线程中处理完毕，其它的动作放到消息队列，由别的线程来完成后续操作，异步
    * 削峰（建议使用限流方式）
* 引入之后的问题
    * MQ需要保证高可用/消息不丢失/消息重复消费/多个下游系统数据要一致
* 消息队列对比
    * 吞吐量/失效性/可用性/可靠性/社区/其它
    * rabbitmq
        * w级/微秒级延迟/主从实现高可用/ack确认机制/社区活跃/基于erlang并发能力高
        * erlang语言问题导致无法深入/吞吐量低于kafka
    * rocketmq
        * 10w级/豪秒级延迟/分布式架构高可用/可通过配置实现不丢数据/有风险
    * kafka
        * 10w级/毫秒级延迟/分布式架构高可用/不建议做业务处理/社区比rocket稳定
* 高可用
    * rabbitmq集群模式
        * 启动多个rabbitmq实例，每个queue的数据只存在与一个实例上，但是会把queue的配置信息同步到别的queue实例上，客户端接入的时候，从queue原数据在的实例复制数据到接入实例，再返回
        * 弊端:接入的时候如果随机选择，会导致数据到处复制，如果选择特定的，会出现单点故障，queue在的主节点宕机，必须等到启动恢复才能继续提供服务
    * rabbitmq镜像队列模式
        * 启动多个rabbitmq实例，每个queue会复制多份存储在所有或者多个节点上，通过这种方式保证高可用
        * 弊端:如果数据量足够大，那个每个实例都会复制，是个潜在的隐患
    * kafka（0.8版本之前没有HA方案）
        * 天然的分布式队列，每个topic会有多个partition，partition分散存储在多个broker上，每个partition的数据，原始数据为leader，会有多份follower分散存储在其它broker上面
        * 写数据的时候先写leader，再写follower，所有follower返回ack写入成功，才会返回写入成功
        * 读数据的时候只读leader，所有follower同步完才能被读到
        
* 幂等
    * kafka的consumer会隔一段时间，提交一次offset，保存在kafka中，极端情况下会丢失一次的commit信息，下次启动会重复消费
    * 解决办法
        * 如果后续插入数据库，先select，后update，或者唯一约束
        * 如果操作redis，直接set，天然幂等
        * 状态机制判断
        
* 可靠性
    * rabbit
        * 场景1:生产者到rabbit丢失
            * 事务模式
                * 同步方式，写入失败，立即返回
                * 吞吐量会降低
            * confirm模式
                * 异步方式，会有ack或者nack回调，监听实现重试
        * 场景2:rabbit内存中丢失
            * 开启持久化
                * 创建queue的时候设置持久化，开启的是队列元数据的持久化
                * 发送消息的时候，设置消息deliveryMode为2，数据先持久化到磁盘再发送
            * 仍然会有极端情况下，来没持久化，就宕机
        * 场景3:消费者拉去到消息，没消费丢失
            * 关闭自动ack
            * 消费者处理完成响应手动ack
    * kafka
        * topic的`replication.factor`大于1，保证至少2个副本
        * kafka server的`min.insync.replicas`大于1，至少有一个follower
        * producer的`acks=all`，写入所有follower才算写入成功
        * producer的`retries=MAX`，写入失败就重试，直到成功

* 顺序
    * rabbit
        * 多queue模型，每个queue的消费者单线程
    * kafka
        * 根据规则指定某些id相同的数据，落入同一个partition，取出来之后，放入内存队列，再处理
        
* 数据积压解决办法
    * 如果数据可以重新写入mq，那么直接丢弃，写consumer直接消费掉，可以扩容消费
    * 如果数据重要，排查consumer问题，修复后，扩容部署
    * 如果数据重要，进mq之前写日志吧，万一崩了，还有路可以走，不然就只能跑了


## [kafka](http://kafka.apache.org/documentation/)
* 名称解释
    * topic
        * kafka按照topic来区分消息，一般topic用来做业务区分标识，即一个topic存储一类消息
    * offset
        * kafka消息按照顺序存储，offset相当于消息id，通过控制offset可以控制消费者消费数据的位置
    * partition
        * 消息存储的时候会分布存储在多个partition中，每个partition上可以保存消息的顺序
        * partition分区在集群模式下，不同分区可以存储在不同机器上
        * leader
            * 分区对应消息存储在某台机器上，该机器即为该分区的leader，处理对该分区的所有读写请求
        * follower
            * 别的机器上存储了该分区的数据的拷贝
            * 做为leader的副本，当leader挂掉的时候，可以转变成leader
            * 当写数据时，必须要求写多少follower成功，才算写成功//todo
    * consumer group
        * kafka由于数据做了持久化存储，所以消费者的个数与数据本身并无关系
        * 每个consumer group会拿到一份完整的数据信息，消费位置可以自由控制
    * consumer instance    
        * 当启动应用实例的时候，需要指定consumer group，consumer group一样的多个实例会组成一个消费者组，消费同一个topic下的数据
* 基本操作
    * 服务启动
    * 创建topic
    * 查看topic列表
    * 查看topic配置信息
    * 启动命令行producer
    * 启动命令行consuer
    * 集群环境配置
* 版本特性
    * //todo