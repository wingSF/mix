# AbstractQueuedSynchronizer
* 这个是什么东西
    * Provides a framework for implementing blocking locks and related synchronizers (semaphores, events, etc) that rely on first-in-first-out (FIFO) wait queues.
        * 首先是一个框架(framework)
        * 为了实现阻塞锁，或者其他的同步器
        * 依赖一个FIFO的队列实现
    * 核心注释解读(原文英文可以查看javadoc，这里只写自己总结的喽)
        * 通过一个原子的int类型(javadoc这么写的，实则不然)的status代表同步器的状态
            * status不是AtomicInt，但是被volatile标识了，且更新的时候使用了UNSAFE，保证了原子性和可见性
        * 子类必须重写被protected修饰的改变status值的方法
        * 子类通过不同的status表示当前对象是被获取了还是被释放了
        * 所有的排队和阻塞的机制由抽象类中的其他方法实现
            > 这些被掩盖的东西才是最核心，最底层，最值得撕逼的代码，当然也有一种思想认为，他是个工具，能理解最好，不理解也无所谓  
            但是在一些必须排个顺序的场景，这些东西将是决定你名次的关键因素
        * 同时支持共享和排他俩种模式，一般只实现一种
        * 内嵌一个```ConditionObject```，它实现了```Condition```接口，用来支持排他模式下的方法实现。
            > 注意该类的其他方法不应该创建该对象。
        * 该类的对象在反序列化的时候，queue是空的，如有需要，请重写```readObject```方法
        * 使用AQS的时候，可以重写5个方法(参见AQS javadoc Usage部分)，实现状态变化。重写要求，内部必须是线程安全的，执行时间应该很短，且应该无锁
        * ```AbstractOwnableSynchronizer```类中有一些追踪锁的持有线程的方法
* 成员变量介绍
    * 静态
        * ```static final long spinForTimeoutThreshold = 1000L```
            * 使用逻辑
                * 某个自旋操作带有超时时间，通过超时时间+```System.nanoTime()```可以确定最后期限
                * 在自旋过程中，最后时间-当前时间可以确定当前距离最后期限还有多久
                * 当这个时间大于这个配置值时，不开启自旋，直接调用```LockSupport.parkNanos(this, nanosTimeout)```
                * 当这个时间小于这个配置值时，通过自旋来搞
        * 下面成员变量是为 通过Unsafe类，调用native方法，高效的修改对象的成员变量属性
            * ```private static final Unsafe unsafe = Unsafe.getUnsafe()```
            * ```private static final long stateOffset```
            * ```private static final long headOffset```
            * ```private static final long tailOffset```
            * ```private static final long waitStatusOffset```
            * ```private static final long nextOffset```
            > 上述的值会在静态代码块中被初始化 
    * 非静态
        * ```private volatile int state```
            * 标识同步状态
        * ```private transient volatile Node head```
            * FIFO队列的头元素，只能通过setHead方法修改，且当head存在时，它的waitStatus不应该是CANCLLED
        * ```private transient volatile Node tail```
            * FIFO队列的尾元素，只能通过enq方法修改
* 内部类
    * Node
        * 
    * ConditionObject
    
       