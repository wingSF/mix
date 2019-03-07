#hashmap
## 四种内部类
### Node类的内部类
* 该内部类主要用于描述hashmap的数据结构中，数组的某个下标位置的数据结构
* 继承关系
    * --->继承关系    - - - >实现关系
    * TreeNode ---> LinkedHashMap.Entry ---> HashMap.Node - - - > Map.Entry
#### Node内部类
* 成员变量
    * final int hash值
    * final key
    * value
    * Node<K,V> next
* 方法(全部final)
    * toString() 输出 "key=value"
    * hashCode() 输出 Objects.hashcode(key)^Objects.hashcode(value)
    * setValue(V newValue) 设置 value=newValue，返回原有的value值
    * equals(Object o) key.equals(key)&&value.equals(value)时为true
#### TreeNode内部类
> 设计红黑树的实现就在这里，先放过他


#### 为什么说hashmap线程不安全
* put方法是线程不安全的，整个方法中包括判断slot槽是否为空，next元素是否为空，等都只能实现基本的单线程操作，如果多线程操作同一个hashmap对象，会出现数据覆盖的情况
> 环形链表的形成是因为并发扩容，根本原因是扩容采用了头插法，而死循环的出现是因为出现了环形链表后，调用了get方法


#### 问题记录
> 在jdk1.7的HashMap的实现中，resize方法会调用transfer()方法，该方法会有一个造成死循环的可能，在jdk1.8中已经被修复  
问题的原因是HashMap在并发扩容的过程中，在旧数组中的元素向新数组迁移的过程中，实现方式采用了头插法，及后插入数组链表中的元素位于先插入的前面，极端情况下，会出现循环链表，导致下次查询命中该数组区的时候，查找过程中产生死循环  
极端情况:并发resize、同一个哈希槽下的链表数据，哈希后还在同一个槽下  
详解:  
线程1操作slot[i]，slot[i]=o1，o1.next=o2,o2.next=null  
线程2同时操作slot[i],在线程1执行执行o2插入的时候，抢先完成扩容，结果是新slot[x]=o2,o2.next=o1,o1.next=null  
线程1继续执行，读取o2的next，读到o1(读到被别的线程修改的数据)，将o1的next节点指向o2，这时循环链表就形成了  
jdk8的改进方案，将头插法，改成了尾插，避免了这种情况

> 在jdk1.8中ConcurrentHashMap，存在死循环情况，1.9修复
//todo一探究竟





