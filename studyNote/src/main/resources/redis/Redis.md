# redis命令
## [推荐学习网站](http://try.redis.io)
* string
    * set name wing
    * set age 3
    * mset name wing age 3
    * mget name age
    * exists name
    * expire age 5
    * setex name 5 wing
    * setnx name wing
    * incr age
    * incrby age +/-50
> 适用于简单的k-v存储    

* list
    * rpush key value1 value2 value3
    * lpush key value1 value2 value3
    * rpop key
    * lpop key
    * llen key
    * 慢操作   
        * lindex key index
        * lrange key start end
            * 负数表示倒数第几个
        * ltrim key start end
> 适用于队列操作，可以左右俩边同时操作，还可以一次性获取多个值
        
* hash
    * hset name field value
    * hmset name field1 value1 field2 "val ue2"
    * hgetall name
    * hget name field
    * hexists name field
    * hincrby name field argument
    
* set
    * sadd key value
    * smembers key
    * scard key    
    * sismember key value
    * spop key [count]
> set是一个去重的集合，本身无序，可以用来判断是否存在操作  
sunion操作可以将俩个set组合在一起
    
* zset
    * zadd key score value
    * zrange key 0 -1
    * zrevrange key 0 -1
    * zcard key 
    * zscore key value
    * zrank key value 
    * zrevrank key value
    * zrem key value
    * zrangebysocre key -inf score
> zset在set的基础上，增加了排序的功能
    
* bitmap
    * setbit key index value[0,1]
    * getbit key index
    * bitcount key
    * bitcount key start end
    * bitpos key value
    * bitpos key value start end
    
* hyperloglog
    * pfadd key value
    * pfcount key
    
* bf
    * bf.add key value
    * bf.exists key value
    * bf.madd key value1 value2
    * bf.mexists key value1 value2
    

# redis动态字符串
* redis存储字符串的时候，采用了预先分配机制，capacity大于字符串的len，如果占用空间1M以内，每次增长都翻倍，超过1M，每次增加1M

# redis的ziplist quicklist
* 在数据量较少的情况下，将数据连续存储，采用ziplist，省去了prev和next的开销，多个ziplist+双向链表指针，形成了quicklist
* 问，当list为空的时候，pop会如何
    * 现象，没有数据之后，循环pop，cpu浪费
    * 解决办法，pop到空，sleep，再pop
    * 又出现现象，数据因为sleep控制的不好，出现了延迟情况
    * 解决办法，blpop，brpop
    * 又出现问题，长时间的等待，占用连接，会被服务器回收
    * 解决办法，try catch +重试
    

# redis的渐进式hash
* redis的rehash过程在数据量很大的时候，会比较慢，采用了渐进式的rehash操作（//tood）保证高性能

# redis的set
* 无序，字典类型，value都是null
* 可以用来做去重

# redis的zset
* 有顺序
* 能去重
* 底层是跳跃链表   

# redis分布式锁
* 原理1
    * 第一个客户端setnx key value1，返回执行成功
    * 第二个客户端setnx key value2，则一定返回失败，直到key过期
* 原理2
    * 第一个客户端setnx之后，为保证key永不删除，设置过期时间
    * setnx key value
    * expire key value
* 原理3
    * 在2中由于是俩条指令，极端情况下会有问题
    * 感谢redis 2.8版本，将俩条指令合并
    * set key value ex expiretime nx
-------------------------------------------------------------------
* 原理4
    * redis分布式锁，没法解决超时问题。如果执行业务逻辑超时了会出现数据问题
* 原理5
    * 在哨兵模式下，极端情况下会出现写主，主宕机，读变主，数据未同步的情况

# redis bitmap
* 普通数据占用的空间巨大，可以使用bitmap进行优化

# redis hyperloglog
* 统计数据会有误差，但是很大数据量才会出现，对于pv，uv的统计最适合
* 会节省很多空间
* 根据低位连续0的个数，估算数字的大小 //todo

# redis 布隆过滤器
* redis 4.0
* 输入的时候，将某个key经过多个hash函数，分散存储
* 判断的时候，同样hash，如果结果中某个位置为0，则一定不存在。

# redis 单线程
* io模型
    * 非阻塞io
        * read方法需要增加一个参数n，当读取不满足n的时候，会阻塞等待
        * write方法只有在缓冲区满了的时候，才会阻塞
        * 通过non_blocking选项，读到不能在读，写到不能再写，返回操作了多少字节
    * select事件轮询/多路复用
        * 非阻塞模型，线程读了一部分数据之后，就直接返回了，什么时候可以继续再读，需要事件通知
        * 解决办法，事件轮询api
            * 操作系统提供的select函数，输入可读或者可写事件，阻塞timeout事件，直到事件到来，或者超时，超时则触发重试。这个循环叫事件循环，是个死循环
            * 由于select在性能上存在问题，这类操作的改进型，epoll（linux），kqueue（macos）
* 指令队列/响应队列
    * redis会将所有的连接都关联一个指令队列/响应队列
    
# redis持久化
* 会fork子进程进行数据持久化，子进程刚产生的时候和父进程共享内存中的数据段和代码段
* 子进程只会持久化内存数据，不会修改，父进程才能改变数据结构
* AOF
    * 将操作命令写入日志，后续重放
* RDB
    * 将内存中的数据写入磁盘
* redis 4.0增加了混合持久化





