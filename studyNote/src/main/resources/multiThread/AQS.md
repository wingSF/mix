# AQS abstract queue synchronizer
* 核心思想
    * 如果被请求的共享资源空闲，则将当前线程标记为有效线程，将共享资源设置为锁定状态
    * 如果被请求的共享资源被占用，就需要一套线程阻塞等待以及被唤醒时锁分配的机制
    * 这个机制AQS是通过CLH队列实现的，及将暂时获取不到锁的线程加入到队列中
        * CLH是一个虚拟的队列，通过内部类Node实现
* AQS对资源的占用方式
    * 独占（Exclusive）
    * 共享（Share）
* AQS涉及的设计模式
    * 使用模版方法模式，自定义同步器需要实现其抽象方法
    * `isHeldExclusively()//该线程是否正在独占资源。只有用到condition才需要去实现它`
    * `tryAcquire(int)//独占方式。尝试获取资源，成功则返回true，失败则返回false`
    * `tryRelease(int)//独占方式。尝试释放资源，成功则返回true，失败则返回false`
    * `tryAcquireShared(int)//共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源`
    * `tryReleaseShared(int)//共享方式。尝试释放资源，成功则返回true，失败则返回false`
* 实现类
    * Semaphore中的 Sync FairSync Sync
        * 允许多个线程同时访问某个资源
    * ReentrantLock中的 Sync FairSync NonfairSync
        * CyclicBarrier的实现使用了该类
    * ReentrantReadWriteLock中的 Sync FairSync NonfairSync
    * CountDownLatch中的 Sync
        * 可以实现让某个线程等到知道倒计时结束(等待别的线程执行结束)
        * 实际使用过程中，主调用线程会开辟多个线程进行工作，初始化count=线程数量，每个线程执行结束，count cas减1，当count为0时，unpack()唤醒主线程

> CountDownLatch与CyclicBarrier的比较