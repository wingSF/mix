# Map
* 双列结合 key-value模式
* map不能存储相同的key
* 每个key都能获取最多一个value
* 提供三种view
    * key的set集合
    * value的collection集合
    * key-value对应的set集合
* 排序需要自身实现
    * TreeMap
* 使用mutable objects作为key的时候要小心
* 不允许将map本身作为key
* 将map本身作为value的时候，equals/hashcode方法no longer well defined on such a map.
* Map的实现类，都应该提供俩中构造方法
    * 空参数构造
    * 带有一个Map类型的参数