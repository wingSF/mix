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
        * field
            * ```volatile int waitStatus```
                * 是一个状态字段，用于sync node(初始化值0)和condition node(初始化值-2)俩种场景。
                * 该字段使用CAS方式或者volatile write方式进行修改
                * 取值有以下几种
                    * -1(SIGNAL signal)
                        * 当前节点的后继节点处于block状态，当前节点释放或者取消的时候，需要唤醒后继节点
                        * To avoid races, acquire methods must first indicate they need a signal, then retry the atomic acquire, and then, on failure, block.
                    * 1(CANCELLED cancelled)
                        * 当前节点已经被取消，因为超时或者线程中断。
                        > 天杀的Doug Lea，代码里面从来不写=1，判断条件都是>0,所以当看到waitStatus>0，其实就是在说这个状态
                    * -2(CONDITION condition)
                        * 当前节点在condition队列中
                    * -3(PROPAGATE propagate)
                        * 不太懂。。。//todo
                    * 0
                        * sync node的初始值
            * ```static final Node SHARED = new Node() | static final Node EXCLUSIVE = null```
                * 标识当前Node是共享模式还是排他模式
            * ```volatile Node prev```
                * 指向前驱节点用来检查waitStatus
            * ```volatile Node next```
                * 指向后继节点，当前节点释放时，会唤醒后继节点
            * ```volatile Thread thread```
                * 记录将当前节点入队的线程，构造节点时指定，使用后改为null
            * ```Node nextWaiter```
                * 区分是共享模式还是排他模式
                * 当为排他模式时，指向condition queue的后继节点
        * method
            * ```final boolean isShared()```
                * 根据nextWaiter判断，是否是SHARED模式
            * ```final Node predecessor()```
                * 返回prev，如果为null，则抛出空指针异常
            * 三个构造
                * 无参构造
                    * 用于SHARED标记Node初始化
                * 用于添加sync后继节点
                * 用于添加condition后继节点
    * ConditionObject
        * 是Condition接口的一个实现，作为AQS中锁的基本实现
        * 该类是可序列化的，但是所有的field都是transient修饰的，所以反序列化该队列，拿不到waiters信息
        * field
            * ```private transient Node firstWaiter;```
                * condition队列的头
            * ```private transient Node lastWaiter;```
                * condition队列的尾
        * method    
            * 暂时跳过
            * //todo 
* 核心方法介绍
    * 先来点简单的
        * 见名知意型
            * ```compareAndSetHead```
            * ```compareAndSetTail```
            * ```compareAndSetWaitStatus```
            * ```compareAndSetNext```
        * 逻辑炒鸡简单的
            * Node内部类
                * ```final boolean isShared```,根据nextWaiter是否是SHARED，判断是否是共享模式
                * ```final Node predecessor```,判断当前node对象是否存在prev，存在返回，不存在报错
            * ```protected final int getState```
            * ```protected final void setState```
            * ```protected final boolean compareAndSetState```
            * ```static void selfInterrupt```
            * ```private final boolean parkAndCheckInterrupt```
                * 挂起当前线程，直到被中断或者被别的线程unpark
                * 返回当前线程的中断标志位
              
            * Queue inspection methods   
                * ```public final boolean hasQueuedThreads```
                    * 判断head和tail是否相等，返回
                * ```public final boolean hasContended()```
                    * 判断head节点是否为null
                * ```public final Thread getFirstQueuedThread | private Thread fullGetFirstQueuedThread```
                    * 第一个方法调用第二个方法，获取最早进入队列的线程
                    * //todo，第二个方法中有重复代码，这个还需要跟进分析
                * ```public final boolean isQueued(Thread thread)```
                    * 从tail开始遍历prev，如果发现某个节点的thread是入参，则返回true，否则返回false
                * ```final boolean apparentlyFirstQueuedIsExclusive```
                    * //todo
                * ```public final boolean hasQueuedPredecessors```
                    * 返回队列当中是否有等待的线程
                    
            * Instrumentation and monitoring methods
                * ```public final int getQueueLength ```
                    * 从tail开始遍历prev，只要节点的thread不为null，就+1，最后返回计数器
                * ```public final Collection<Thread> getQueuedThreads```
                    * 从tail开始遍历prev，只要节点的thread不为null，就加入集合，然后返回该集合
                * 以下俩个方法，通过调用节点的isShared()，来决定是否加入集合
                    * ```public final Collection<Thread> getExclusiveQueuedThreads```
                    * ```public final Collection<Thread> getSharedQueuedThreads```
                * ```toString```
                    * 返回当前的status，queue是否为空
            
            * Internal support methods for Conditions
                * ```final boolean isOnSyncQueue(Node node) | private boolean findNodeFromTail(Node node) ```   
                    * 俩个方法配合，判断某个node是否在sync queue上
                * ```final boolean transferForSignal(Node node)```
                    * 把一个节点从codition queue转移到sync queue
                * 
        * 单独看懂，后期需要结合别的方法加深整体理解的
            * ```private void setHead(Node node)```
                * 将某个node，设置为头，直接出队，只应该被acquire方法调用
                * 将node的thread和prev清空
            * ```private void unparkSuccessor(Node node)```
                * 获取入参node的waitStatus，如果该状态为<0，则更新为0
                * 然后获取node.next赋值给s，如果为null或者状态>0,则从tail开始，for循环node.prev，一直到null或者node结束
                    * 循环过程中如果发现waitStatus<=0的节点node，则赋值给s，以便后续操作
                * 如果s不为null，则使用```LockSupport.unpark(node.next.thread)```唤醒
        * 需要子类实现的
            * ```tryAcquire```
            * ```tryRelease```
            * ```tryAcquireShared```
            * ```tryReleaseShared```
            * ```UnsupportedOperationException```
        * 来点难度
            * ```private Node enq(final Node node)```
                * 该方法用于将一个node插入到队列中，如果需要做初始化操作
                * 第一行``` for (;;) {```，方法体中，只有一个return，即是出口
                * ```Node t = tail;```,取出当前AQS的子类对象的tail成员变量赋值给t
                * 如果tail是null的时候，说明还没有被初始化过，此时做初始化操作
                    * 使用new Node()创建新的Node对象，然后CAS，将head更新为该node对象，如果成功，将该对象同时赋值给tail，如果失败，则说明别的线程完成了上述操作
                * 初始化完成后，进行node入队操作
                    * 先将tail保存的对象赋值给方法入参node的prev
                    * CAS更新tail，从原来的t(old tail)变成node(new tail)，如果操作成功，设置t的next为node，然后返回。如果失败，说明tail已经别的线程修改，需要重新获取tail，重试，直到成功
                * 最后返回原先的尾巴节点tail，即现在tail节点的前驱节点
                * 被调用位置
                    * ```addWaiter```  
                    * ```transferForSignal```
                    * ```transferAfterCancelledWait```
            * ```private Node addWaiter(Node mode)```
                * 创建节点对象并加入等待队列，共享还是排他由参数决定
                * 如果tail节点不为空，操作与enq方法的后半部分一致，然后返回新创建node对象
                * 如果tail节点为空，直接调用enq方法，然后返回新创建node对象
    * 重点来喽
        > AQS自身不能直接作为同步器来使用，所以可以借助几个实现类，这样可以从入口把上面零散的方法穿插起来    
        * 公平锁/非公平锁 | 共享锁/排他锁  从ReentrantLock开始~
            * ReentrantLock在构造的时候可以根据参数选择用公平还是非公平，以非公平做例子(逻辑比公平要简单一个方法)
            * lock方法调用链路(只分析复杂流程中的复杂方法) 
                * ReentrantLock#lock() -> Sync#nonfairTryAcquire() -> AbstractQueuedSynchronizer#addWaiter() -> AbstractQueuedSynchronizer#acquireQueued()
                    * 在上述调用链路中，会根据前面方法的返回值来决定是否会继续后面的方法，自行推断下吧
                    * nonfairTryAcquire()
                        * 该方法先获取当前线程和status值
                        * 判断status是否为0,
                            * 是
                                * cas更新status
                                    * 更新成功
                                        * 设置当前线程持有锁，通过AQS的父类AOS中的Thread成员变量保存，返回true，后续方法不再执行，直接返回
                                        > 此处就是非公平的原因所在，后续分析为啥，以及与公平锁的对比
                                    * 更新失败
                                        * 返回false，接着执行后续方法
                            * 不是
                                * 判断排他Thread是否是当前线程
                                    * 是
                                        * 将status累加，然后设置给status，返回true，后续方法不再执行
                                        * 此处注意，被volatile修饰的status，在内存中进行，增加再set的操作本身是线程不安全的，但为啥ReentrantLock这么写确没问题呢？
                                        > 因为，进入该分支的前提是，排他线程是当前线程，也就是说，这个status变量只会被这一个线程操作，所以不会存在线程不安全问题
                                    * 不是
                                        * 返回false，接着执行后续方法
                    * addWaiter(Node.EXCLUSIVE)
                        * 该方法尝试将当前线程，封装在一个node节点内部，然后将该node节点设置为当前锁对象的sync字段的tail字段
                        > 自己学习的时候，经常分不清类似tail这些值到底是谁的是哪个属性，是AQS的还是NonFailLock的还是Lock的。。。
                        * 实际Node.EXCLUSIVE是个null，所以入参mode是null
                        * 新建的node节点，thread是当前线程，nextWaiter为null
                        * 获取当前ReentrantLock的sync字段的tail字段
                            * 如果tail不为空，将新建的node节点的prev设置为tail节点，尝试cas更新tail节点为node
                                * 成功
                                    * 将tail节点的next设置为新建的node节点
                                    * 返回新建的node节点
                                * 失败
                                    * enq(新建的node节点)
                                    * 返回新建的node节点
                    * enq(node)
                        * 将tail节点替换为node节点
                        * 如果tail节点为null的话，先初始化tail=head=new Node(),然后再替换
                * acquireQueued()方法的逻辑又是一个重中之重
                    * 调用链路 tryAcquire(arg) -> shouldParkAfterFailedAcquire(p,node) -> parkAndCheckInterrupt() -> cancelAcquire()
                    * 先判断node的前驱节点是不是head，再判断tryAcquire()的返回值
                        * 进入循环体
                        * true(表示满足 锁被当前线程持有，且node是head的后继节点)
                            * 将node设置为head节点
                            * 将node的前驱节点置空
                            * failed局部变量设置为false，finally中的cancelAcquire不会执行
                            * 返回false
                        * false
                            * shouldParkAfterFailedAcquire(p,node) && parkAndCheckInterrupt()
                                * shouldParkAfterFailedAcquire
                                    * 对于acquire失败的node，检查前驱节点的waitstatus，当状态为Node.SIGNAL，或者将waitStatus修改为该状态，则返回true。
                                    * Node.SIGNAL表示节点release的时候会唤醒next节点
                                    * 如果返回false，则继续下次循环
                                * parkAndCheckInterrupt()
                                    * 只有上一个方法返回true，才会执行该方法
                                    * 该方法就是将当前线程阻塞，直到unpark或者interrupt
                                    * //todo LockSupport
                            * 当上述条件返回true，也就是获取锁失败，进入同步队列阻塞，并再次被唤醒时，发现中断标志位为true，设置interrupted局部变量为true
                            > 在acquireInterruptibly的方法里面，这里的处理方式是抛出中断异常
                    * 注意finally块中的代码，正常逻辑是不会执行的。当内部发生异常，会调用cancelAcquire方法 
                    * cancelAcquire(node)
                        * 获取前驱节点，并检查状态，将超时的被cancel的节点全部跳过
                        * 如果node是tail，则尝试把tail，从node改成node.prev，成功后将pred的next修改为null。实质上完成了node节点的移除操作
                        * 如果node的prev是head节点，调用unparkSuccessor(node)
                            * unparkSuccessor方法，会查找node的next节点，然后唤醒next节点的线程
                            > 查找方法和条件此处就不写了
                        * 如果node节点不是head，则尝试将自己从等待队列中移除
                        > 详细实现过程，不再详述
            * unlock方法过程相对简单
                * 获取status值，减去release的数量，比对exclusiveOwnerThread，判断剩余是否是0，如果0，就唤醒head的后继节点，如果不是，则不进行唤醒操作
            * 为什么是非公平的
                * 上文中，有一个位置，特定备注了下，非公平的原因就是那里造成的，与公平锁的代码的区别，也就那一个地方
                * 在非公平锁中，只要status为0的时候，新来的线程和被唤醒的线程进行竞争，如果新来的cas成功，则被唤醒的线程只能继续沉睡，这个是不是很不公平
                * 如果一直是新来的线程获取锁成功，可能会出现所谓的饿死的情况。
                * 在公平锁的实现中，这个地方，会调用hasQueuedPredecessors()，判断是否有阻塞等待的节点，来确认是否执行cas操作
                * 但是非公平锁的效率要比公平锁略高，因为这个判断是多出来的逻辑，很显然会耗时
            * 公平锁
                * 只分析hasQueuedPredecessors(),别的逻辑与非公平一致
                * hasQueuedPredecessors()
                    * 当该方法返回true的时候，外层直接返回获取锁失败，进入等待队列 | 当该方法返回false的时候，执行与非公平锁一样的逻辑
                    * 方法作用是，查询是否用线程比当前线程等待的时间还要长
                    > javadoc原文 Queries whether any threads have been waiting to acquire longer than the current thread.
                    * ```h != t && ((s = h.next) == null || s.thread != Thread.currentThread());```
                    * h指head，t指tail，s指head.next
                    * 总结下
                        * 返回true的场景
                            * 头尾不相等，s为null，返回true
                            * 头尾不相等，s.thread不是当前线程，返回true
                        * 返回false的场景
                            * 头尾相等，返回false
                            * 头尾不相等，s不为null，s.thread还是当前线程，返回false
                            > 这个场景可能会出现么?当前线程已经在等待队列中被block，然后自己又来了，这是个什么场景，百思不得解释，希望大家用来反问牛逼的面试官，然后分享下...//todo