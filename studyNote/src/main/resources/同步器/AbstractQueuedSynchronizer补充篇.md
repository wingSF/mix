# AbstractQueuedSynchronizer补充篇
* acquiredShared
    * 以共享的方式获取资源，可以在jdk的Semaphore中找到实现，同样有Fail/Nonfair俩种实现.CountDownLatch中也有对应实现，但是没有公平非公平区分
    * Nonfair
        * ```nonfairTryAcquireShared```,for循环+cas，直到成功
    * Fair
        * 调用```hasQueuedPredecessors```判断是否sync队列中是否有等待node，且node的线程不是当前线程
        * 如果存在，说明有别的线程先来，一般实现是排队再这个线程后面，来保证公平访问
    * 在上述实现中，循环cas操作成功后，不再调用```setExclusiveOwnerThread```
    * release的过程中，也不再判断持有锁的线程，也不讲持有锁的线程更新为null