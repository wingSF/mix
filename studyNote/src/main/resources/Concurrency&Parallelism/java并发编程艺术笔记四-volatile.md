# volatile
* 被volatile修饰的变量的读写操作，是原子操作，且操作结果会刷新写缓冲区，保证别的线程可见
> 只保证volatile变量的读写操作是原子操作，每次读都能读到(任意线程)对该变量的上一次写入
* 从JSR-133开始(JDK5)之后，volatile变量的读写操作能够实现线程之间的通信
* 如何保证上述特性呢
    * 写一个volatile变量，JMM会把当前线程对应的本地内存中的共享变量的值刷新到主内存中
    > 效果是当前线程对接下来要读取该变量的线程发送了一个消息
    * 读一个volatile变量，JMM会把当前线程本地内存置为无效，线程将从主内存读取该变量的值
    > 效果是读取该变量的线程得到了前一个线程发的消息
    * 最终效果完成了俩个线程的通信
* 底层的实现方式
    * 在volatile变量的写指令前插入一个 StoreStore 内存屏障
    * 在volatile变量的写指令后插入一个 StoreLoad 内存屏障
    * 在volatile变量的读指令前插入一个 LoadLoad 内存屏障
    * 在volatile变量的读指令后插入一个 LoadStore 内存屏障
    > 在实际计算机执行的时候，会根据实际情况，在不改变语义的情况下，进行一些优化，去除无用的屏障
* 内存屏障  
    > 内存访问指令:存储和装载指令
    * StoreStore 
        * 常用指令示例: store1 StoreStoreBarrier store2
        * 保证 store1数据对其它线程可见 先于 store2及store2后续的所有存储指令 的存储
    * StoreLoad  
        * 常用指令示例: store1 StoreLoadBarrier load2
        * 保证 store1数据对其它线程可见 先于 load2及load2后续的所有装载指令 的装载
    * LoadLoad
        * 常用指令示例: load1 StoreLoadBarrier load2
        * 保证 load1数据的装载 先于 load2及所有后续装载指令 的装载
    * LoadStore
        * 常用指令示例: load1 StoreLoadBarrier store2
        * 保证 load1数据的装载 先于 store2及所有后续存储指令 刷新到主内存
* JSR-133前后对比
    * 前
        * 不禁止volatile变量和普通变量操作的重排序
        * 不能达到锁的语义
    * 改进目标
        * 为了提供一种比锁更轻量级的线程通信方式
    * 后
        * 通过插入内存屏障，禁止volatile变量和普通变量操作的重排序
        * 达到类似锁的语义
        
* volatile和锁的对比
    * volatile只能保证对单个变量的读写是互斥的
    * 锁可以保证整个临界区的代码是互斥执行的
    * volatile的性能比锁更佳