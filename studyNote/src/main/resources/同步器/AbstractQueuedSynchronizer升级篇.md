# AbstractQueuedSynchronizer升级篇
* 简单的通过status，进行状态同步，只依赖lock，unlock已经可以实现
* 试想一个实际场景，实现一个有界队列的添加和移除
    * 当队列满的时候，新来的add线程应该被阻塞，
    * 当读到队列为空的时候，读线程应该被阻塞
    * 同步器中的Condition就是解决这些问题的
* 分析
    * add线程虽然被阻塞，但他一直在等待一个时机，那就是有读线程把数据取走了，队列有空间可以写了
    * read线程虽然被阻塞，但也在等待一个时间，那就是有数据被加入到有界队列中
    * 所以会有一种需求，当向有界队列中成功添加元素后，要唤醒一个等待的读线程取走数据。同理，当读取到空的时候，需要唤醒一个add线程，添加数据。
* 问:到底什么东西是个Condition？
    * 终于在Condition接口的注释中找到了答案
    > Where a {@code Lock} replaces the use of {@code synchronized} methods and statements  
     a {@code Condition} replaces the use of the Object monitor methods.
    * lock取代了synchronzed关键字，condition则取代了wait/notify/notifyAll的通知机制
* lock/condition既然能取代synchronized/wait/notify，那一定是原来的实现有问题
    * 问题
        * synchronized遇到异常会自动释放锁，改进后的lock必须调用unlock方法才可以
        * synchronized不会响应中断，lock有响应中断的api，甚至有等待超时的api，这样对于锁的控制将会更加灵活。 
        * 传统的object锁，只能提供一个锁定队列，但是lock/condition可以提供多个等待队列
        * 传统的object锁，在唤醒的时候，无法指定线程唤醒，但condition可以指定唤醒，默认唤醒等待最久的线程
        * 传统的object锁，在唤醒的时候，必须持有指定的对象，但condition不需要。ex:有界队列的实现中，写入数据之后，唤醒读线程
* 核心方法解析
    * await
        * 调用链路```addConditionWaiter```->```unlinkCancelledWaiters```->```fullyRelease```