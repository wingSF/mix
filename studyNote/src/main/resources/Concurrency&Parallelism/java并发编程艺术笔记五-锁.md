# 锁
* 作用:控制多个线程访问共享资源的方式，一般情况下，一个锁能够防止多个线程同时访问共享资源，但有些锁允许多个线程同时访问共享资源，如读写锁
* 锁释放会把线程对应的本地内存中的共享变量刷新到主内存中
* 锁获取会把线程对应的本地内存中置为无效，从而保证临界区的变量读取可以用主内存中获取。

## lock与synchronized对比
* synchronized中锁释放是自动的，lock中是手动的
* lock的api使加锁解锁更加可操作
* lock可中断的获取锁
* lock获取锁可以增加超时控制
* 编码时注意，lock.unlock要放在finally中，保证一定解锁，但是lock.lock不要放在响应的try里面，不然会出现错误的解锁操作

## lock
* 尝试非阻塞的获取锁
    * 当前线程尝试获取锁，如果锁没有被其它线程获取，则当前线程获取成功
* 能被中断的获取锁
    * todo，这个理解不了 page222
* 超时获取锁
    * 在指定的截止时间之前获取锁，如果截止时间到了仍无法获取锁，则返回
    
## lock api
* lock
    * 获取锁，如果被别的线程获取，就等待，直到获取成功
* lockInterruptibly
* tryLock
    * 以非阻塞的方式尝试获取锁，成功返回true，失败返回false
* tryLock(long time,TimeUnit timeunit)
    * 在time到时前获取了锁，正常返回
    * 在time到时前，被中断
    * 超过time，仍没有获得锁，返回false
* unlock()
    * 解锁
* newCondition()
    * 获取等待通知组件，该组件与当前线程绑定，只有获取了锁，才能调用该组件的wait方法，调用后，锁释放
    
## lock aqs
* 使用int型成员变量表示同步状态
* 使用FIFO队列来维护资源获取线程的排队工作
* aqs提供了同步状态的控制/线程的排队/等待和唤醒操作，面向开发锁的程序员，而锁基于aqs，提供面向锁的使用者
* 使用了模版方法设计模式，锁的实现者需要重写抽象方法
* aqs api分为独占式/共享式/独占+超时式3类型
    * acquire 独占+阻塞
        * tryAcquire 尝试独占式获取
        * release   释放
    * acquireInterruptibly  独占+阻塞+响应中段 
    * acquireShared 共享+阻塞
        * tryAcquireShared 尝试共享式获取  
        * releaseShared
    * acquireSharedInterruptibly 共享+阻塞+响应中断
    * tryAcquireNanos   独占+超时时间获取锁
    * tryAcquireSharedNanos  共享+超时
> 读源码的收获，对&& 或者 ||的新理解  
之前一直固执的认为是，俩边条件都满足，然后得到条件的值，再进行后续逻辑  
但是在源码中，分析到了一些逻辑的味道，ex: jdk1.8 AbstractQueuedSynchronizer.class acquire方法  
  
```java
public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
```

> 先调用tryAcquire方法，根据返回结果决定是否执行后续逻辑。

## ReentrantLock
* 重入锁
    * 已经获取锁a的线程，再次尝试获取锁a，非重入锁会阻塞，重入锁可以继续执行
    * synchronized默认是可重入的
* 核心方法
    * lock  
    * unlock
* 内部核心类 Sync extends AbstractQueuedSynchronizer
    * 核心实现 FairSync/NonfairSync
        * 公平和非公平，是否先等待的线程先获取锁资源
        * 非公平锁的tps会比公平锁的高，但是可能会单个线程等待较长时间，叫"饥饿"
    > 在NonfairSync中使用cas操作来更新数据状态
* CAS操作具有volatile读和 volatile写的内存语义
    * cas实际调用的是cmpxchg的指令，根据是否是单处理器运行，确定是否增加lock前缀指令
    * 在Pentium和Pentium之前的处理器，lock指令通过锁总线实现原子操作；从Pentium 4/Intel Xeon及P6处理器开始，使用锁缓存实现，开销更小
    * lock指令
        * 禁止该指令与之前和之后的读写指令重排序
        * 把写缓冲区的数据刷新到内存中
        > 以上俩点做到了类似内存屏障的效果，类似volatile读/写的内存语义
* Concurrent中的实现套路
    * 声明共享变量为volatile
    * CAS更新实现同步
    
## 读写锁
* 数据被访问的概率比被修改的概率大很多，所以让读读并行，读写/写写串行能提升效率
* 锁内部持有俩把锁，一个读锁，一个写锁，根据一个32位的状态位，高16位读状态，低16位写状态
* 写锁，可重入，排他
    * 如果读锁已经被获取，等待
    * 如果获取写锁的线程不是自身，等待
* 读锁，可重入，共享
    * 如果有写锁，等待
    
## lockSupport
* 用来阻塞/唤醒线程

## Condition
* 可以在一个对象上维护多个condition等待队列，需要被唤醒的时候，将线程从等待队列移动到同步队列中
* 配合Lock接口，用于实现等待/通知模式

# final
* 规则
    * 当final域为基本数据类型时
        * 在构造函数内对一个final域的写入，与随后把被构造对象的引用赋值给一个引用变量，这俩个操作不能重排序
            * JMM禁止编译器把final域的写，重排序到构造方法外面
                * 通过在final域写 和 构造方法返回中间，插入一个 storestore屏障
        * 初次读一个包含final域的对象的引用，与随后初次读这个final域，这俩个操作不能重排序
            * 本身该操作存在间接数据依赖，所以编译器不会进行重排序
            * 但是alpha处理器会进行重排序，所以加入loadload内存屏障，专门对付该类型处理器
    * 当final域为引用数据类型时
        * 在构造函数内对一个final对象的引用的成员域的写入，与随后把被构造对象的引用赋值给一个引用变量，这俩个操作不能重排序
* 最终效果
    * 在引用变量对任意线程可见之前，保证final域已经正确初始化
* 编码注意
    * 不能将被构造对象从构造方法中逃逸（通过this）
    * why
        * 构造方法内部可能会重排序，一旦对象逃逸之后，就会对别的线程可见，导致线程可能读取到初始化之前的默认值
> 在x86处理器中，不会对写-写操作进行重排序，同时保证数据间接依赖，所以写final域的storestore屏障和读final域的loadload屏障都会被省略
* 为什么这么做
    * 在JSR-133之前会读到变化的final域的值，所以做了增强
    * 通过对final域增加读写操作的重排序规则，可以为Java程序员提供初始化安全保证
        * 只要对象是正确构造的(被构造对象的引用在构造方法中没有逸出)
        * 不需要使用同步就可以保证任意线程都能看到这个final域在构造函数中被初始化的值
