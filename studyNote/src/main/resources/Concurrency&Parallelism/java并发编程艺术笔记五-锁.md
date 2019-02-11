# 锁
* 作用:让临界区的代码互斥执行
* 锁释放会把线程对应的本地内存中的共享变量刷新到主内存中
* 锁获取会把线程对应的本地内存中置为无效，从而保证临界区的变量读取可以用主内存中获取。

## ReentrantLock
* 核心方法
    * lock  
    * unlock
* 内部核心类 Sync extends AbstractQueuedSynchronizer
    * 核心实现 FairSync/NonfairSync
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
    
# final
* 规则
    * 在构造函数内对一个final域的写入，与随后把被构造对象的引用赋值给一个引用变量，这俩个操作不能重排序
        * JMM禁止编译器把final域的写，重排序到构造方法外面
            * 通过在final域写 和 构造方法返回中间，插入一个 storestore屏障
    * 初次读一个包含final域的对象的引用，与随后初次读这个final域，这俩个操作不能重排序
        * 