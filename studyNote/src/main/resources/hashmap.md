#hashmap
## 四种内部类
###Node类的内部类
* 该内部类主要用于描述hashmap的数据结构中，数组的某个下标位置的数据结构
* 继承关系
    * --->继承关系    - - - >实现关系
    * TreeNode ---> LinkedHashMap.Entry ---> HashMap.Node - - - > Map.Entry
####Node内部类
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
####TreeNode内部类
> 设计红黑树的实现就在这里，先放过他
