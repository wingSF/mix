# mybatis缓存
## 一级缓存
* sqlSession级别缓存，sqlSession关闭则缓存消失
* 该及缓存默认开启
## 二级缓存
* 全局缓存，key为namespace+sql+param
* 缓存过期的默认实现是LRU
* 需要配置开启

> 注意:  
一定要求对同一个表的操作，放到同一个mapper.xml也就是同一个namespace下，如果不这样做  
mapper修改某个记录的时候，只会刷新自己的缓存，如果别的mapper已经缓存了该错误的值，就会产生问题喽