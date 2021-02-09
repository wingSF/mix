# dubbo
* 用来解决什么问题
    * 成千上万的微服务节点通信
        * 传统的http域名配置方式，每个服务都会分配一个专属域名
        * 将转发策略配置在nginx或者f5这样的负载均衡器上
        > 当遇到新服务上线，或者已有服务节点上下线，等等操作时，需要运维同学的配置，手动修改部分配置，时间巨长～～～
    * 根据上面问题描述，需要我们开发自己搞一套服务通信方式，能够高效响应业务
        * 动态的服务发现机制
            * 注册中心：一个集中式的存储，里面记录了所有在线上运行着的服务信息
            * 服务注册：当某个服务实例启动时，将自己的服务信息写入注册中心
            * 服务发现：当需要调用某个服务时，从注册中心根据一定条件找到运行中的服务列表
                * 基于服务列表，可以实现负载均衡，failover等功能
            > 至此服务的上下线，动态的调整实例个数，再也不用等申请域名，申请扩容的工单了
        * 线上服务运行时的状态也是我们需要关注的key point
            * 某个服务实例每天的调用量、峰值qps、响应时间
            * 当某个实例响应时间不达标时，我们应该调整其流量，而不应该放任不理
                * 假如超了rt阈值，应该有相应的服务熔断/降级动作
* dubbo核心功能
    * 启动时依赖检查
        * 依赖服务是否正常启动
            * dubbo.reference.check
            * dubbo.consumer.check
        * 自身服务注册是否成功
            * dubbo.register.check
    * 集群容错(org.apache.dubbo.rpc.cluster.Cluster)
        * 一堆名词
            * invoker
                * 代表一个provider服务，封装了服务提供者的地址和接口信息
            * directory
                * 多个invoker封装在directory中，会根据注册中心的推送动态变更
            * cluster
                * cluster将directory中的多个invoke封装成一个invoker提供给前端调用
            * router
                * 负责从directory中按照规则选出invoker子集
            * loadbalance
                * 负责从invoker子集中按照规则选出一个invoker用于本次调用
        * 容错模式
            * failfast
                * 使用for循环，调用invoke子集，如果失败就下一个
            * failsafe
            * failBack
            * forking cluster
                * 底层开启一个线程池，for循环execute任务，然后从对列中pop成功结果
                > question: forking开启的线程池返回一个后别的就不管了？不对别的任务做处理嘛？这个线程池是什么维度的？
            * broadcast cluster
    * 负载均衡(org.apache.dubbo.rpc.cluster.LoadBalance)