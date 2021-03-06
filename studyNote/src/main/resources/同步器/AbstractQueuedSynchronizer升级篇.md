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
        * 调用链路```addConditionWaiter```->```fullyRelease```->```isOnSyncQueue(node)```->```checkInterruptWhileWaiting```->```unlinkCancelledWaiters```->```reportInterruptAfterWait```
        * 以上调用链路，并不是程序的真正执行顺序，只是所有逻辑中会涉及到的核心方法
        * 核心方法
            * ```addConditionWaiter()```
                * 将当前线程封装到一个Node对象里面，status是Node.CONDITION
                * 将该node对象，放在lastWaiter的位置(小学生难度的链表操作)
            * ```fullyRelease(node)```
                * 获取当前同步器的status值，然后调用release方法，清空当前同步器的status值，设置exclusive线程null
                * 效果相当于把node对象从sync队列移除
            * ```isOnSyncQueue(node)```
                * 判断node节点是否在sync队列上
                * 由于上述的release方法，sync队列上已经不存在该node
                * 但为什么还有这个while判断呢?//todo 
                    * 个人猜测原因有，会不会和中断有关系呢，如果线程被中断，locksupport会是一个什么样的反应
                    * LockSupport.park的是线程，由于该线程可能会在别的地方被unpark,所以需要增加该项判断，需要深入LockSupport进行验证
                    * 会不会是由于signal机制决定的，需要根据signal方法的，进行验证
                    * 结论
            * ```checkInterruptWhileWaiting(node)```
                * 每次被unpark之后，检查当前线程是否被中断
                * 如果中断标志位true，则调用```transferAfterCancelledWait(node)```
                    * cas尝试更新node的waitStatus
                    * 成功
                        * 通过enq方法将node添加到sync队列
                    * 失败
                        * 说明被其他线程通过signal，已经移动到sync队列或者正在移动
                        * 根据isOnSyncQueue，无限期yield直到成功
            * 再次通过acquireQueued(node,savedState),尝试获取锁
                * 注意此时一定要获取savedState这个数，否则外层的锁释放将会出现问题
            * ```unlinkCancelledWaiters```
                * 从conditon的firstWaiter开始，将队列中被cancel的清理一遍
            * ```reportInterruptAfterWait(int interruptMode)```
                * 根据interruptMode，不同操作
                * THROW_IF->throw new InterruptedException()
                * REINTERRUPT->Thread.currentThread().interrupt()
    * signal
        * 调用链路```signal()```->```doSignal(first)```->```transferForSignal(Node node)```
            * 核心方法```transferForSignal(Node node)```
                * cas修改node的状态，从condition->0，如果更新失败，返回false
                * 将node通过enq方法，从condition队列转移到sync队列，返回node的前驱节点
                * 判断p的waitStatus，如果<=0,尝试cas更新状态为SIGNAL，如果成功，unpark node的thread
    * signalAll
        * 调用链路```signalAll()```->```doSignalAll(first)```->```transferForSignal(Node node)```
                * 参考signal可理解