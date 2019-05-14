# mycat翻页一
* mycat分页带来的数据错误
    * 使用mysql不指定order by 条件的时候，使用limit start,pageSize 默认采用主键排序
    * 使用mycat不指定order by 条件的时候，mycat只会把数据查询出来，然后merge结果，并返回
        * 这个地方注意了，没有排序的，所以每次返回的数据是动态的，坑爹不，有mycat的可以自己试试看
            * 如果没发现，请将offset和pagesize调大


# mycat翻页二
* 永远不要使用mycat做翻页实现
    即不要在mycat执行 limit  start，pagesize
    * 如果非要，请参考[58沈剑的二次查询法](https://mp.weixin.qq.com/s/H_2hyEqQ70Y_OoFZh_P_5A)，该查询中有个前提，数据要尽量均匀，不然第一次改写会比较复杂

* 例子
    * mysql架构  
        * mycat后面2个mysql
    * 前提  
        * 查询的数据不会落在一个mysql实例上
    * 执行1  
        * select * from user order by id asc limit 0，5
    * 分析  
        * sql要求从user表查询第0条到第5条  
        * 如果要查询这5条数据，如何做  
        * 在mysql1中 order by id asc limit 0，5  
        * 在mysql2中 order by id asc limit 0，5

        * 将数据聚合  
        * 执行order by id asc  
        * 然后limit 0，5

        * 返回

    * 执行2  
        * select * from user order by id asc limit 1，5
    * 分析  
        * sql要求从user表查询第1条到第6条

        * mysql1  
            * order by id asc limit 0，{start}+{pagesize}；  
            * order by id asc limit 0，6；  
        * mysql2  
            * order by id asc limit 0，6；

        > 此处需要好好思虑一下，mycat的这种解决办法如果有更好的方案的话，就能解决mycat翻页的这个问题
        
    * 执行3  
        * select * from user order by id asc limit 1000000，1000
    * 分析  
        * sql要求从user表查询第1条到第6条

        * mysql1:  
            * order by id asc limit 0，{start}+{pagesize}；  
            * order by id asc limit 0，1001000；  
        * mysql2: 
            * order by id asc limit 0，1001000；

    >试问:
    如果数据量足够多，start足够大，一次查询，myslq实例就会乖乖倒下



* 变通办法
```java
    while（resultSize != 0）{
        select * from user where id  > {上次查询的结果集的最后一条的id} order by id asc limit {pageSize}
    }
```  

* 分析  
    * sql要求从user表查询第1条到第6条  
    
    * mysql1  
        * where id > {lastTimeMaxId} order by id asc limit {pagesize}；  
    * mysql2  
        * where id > {lastTimeMaxId} order by id asc limit {pagesize}；

>通过上述可以实现全量数据的查询，且mysql实例的压力只取决与pageSize的大小


#mycat翻页三
* 总结[58沈剑的二次查询法](https://mp.weixin.qq.com/s/H_2hyEqQ70Y_OoFZh_P_5A)
    * 有个前提，数据要尽量均匀，不然定位最小数据后会和目标位置偏差较远，不利于二次定位
    * 总体思想，假设数据均匀分布，当limit offset,pageSize时，在每个分库上执行 limit offset/分库数量，pagesize
    * 在多个结果集中，取最小值，然后确定该最小值，在所有数据中的offset
    * 然后根据当前offset，在尝试获取目标分页数据，会大大降低检索数据量，避免了再每个分库执行 limit 0，offset+pagesize的查询

//todo
logaspect问题
