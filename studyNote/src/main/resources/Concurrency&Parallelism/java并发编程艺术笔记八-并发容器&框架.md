# ConcurrentHashMap
## 为什么使用ConcurrentHashmap
* hash结构，查询复杂o(1)
* 普通的HashMap，不是线程安全的
* 线程安全的HashTable是效率低下的
    * 效率低下的原因是，所有的方法都等待同一把锁
        * 这也是ConcurrentHashMap1.8之前的版本优化的切入点（分段锁）
## 理论依据
* 分段锁原理:hashmap是数组+链表的结构，数组中每个下标下面是一个链表，链表内操作必须同步，但是链表间的操作可以是并行的
* volatile原理:被volatile修饰的变量，读/写都是原子的，所以get方法是不需要锁来保证的，直接读取即可
* put扩容：按需扩容，hashmap是整体扩容，而ConcurrentHashMap是按照segment扩容

## [推荐阅读](http://www.importnew.com/28263.html)

# Queue队列
* 类图
    * ![avatar](/image/Queue类图.png)
> 再次深刻理解了Abs*抽象类的作用。接口用于定义行为，抽象类负责实现接口中提到的通用的行为，具体的实现类要负责特定方法的真正实现。
* 核心接口及抽象类
    * Deque
        * 双向队列
    * BlockingQueue
        * 阻塞队列
        * 实现时基于锁控制
            * BlockingDeque
                * 阻塞双向队列
            * TransferQueue
                * 生产者尝试将元素直接交给消费者处理，如果没有消费者，加入队列
    * AbstractQueue
        * 抽象类，负责通用方法的实现
        * 非阻塞队列可以直接继承该类进行实现，非阻塞队列的实现基于 循环CAS
    
