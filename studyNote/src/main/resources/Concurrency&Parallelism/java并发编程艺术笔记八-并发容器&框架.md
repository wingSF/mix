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
* 某些场景下，需要线程安全的队列，这种队列一般分为俩种，阻塞队列和非阻塞队列
    * 阻塞队列
        * 使用锁实现的队列进出控制
    * 非阻塞队列
        * 使用循环CAS实现的队列进出控制
