#[mysql索引相关by张丰哲](https://www.jianshu.com/p/aa1f0f29b4f8)
#[mysql索引数据结构by沈剑](https://mp.weixin.qq.com/s/YMbRJwyjutGMD1KpI_fS0A)
#[未读](https://notes.diguage.com/mysql/)
#[未读](http://blog.codinglabs.org/articles/theory-of-mysql-index.html)

* mysql读取数据的时候，不是按行读取，而是按Block or Page读取
* delete表数据是基于行的删除操作，trancate是按照block删除，trancate快～
* m join n  和 n join m 区别 ，建议 小数据量 join  大数据量

* 聚集索引和非聚集索引的区别在于，通过索引最终找到的是数据还是数据的地址值，如果是数据叫聚集索引，反之非聚集索引
* innodb是聚集索引，M与ISAM是非聚集索引
* 聚集或者非聚集索引的内层节点，都只存储叶子节点的信息，不存储数据，加快检索效率
* innodb的普通索引的叶子节点存放是主键，然后通过主键索引去查找数据
* MyISAM的普通索引和主键索引的区别，在有主键索引找到数据唯一，所以主键索引找到一条数据就停止，而普通索引不能停止

* B+Tree数据量 N = f(树的高度 h, 索引块的大小 m)
    * N一定的情况下，索引字段越小，m越大，h越小，效率越高
        * 索引只有叶子节点才会存储数据或者数据的地址值，这样可以减小单个m的值，每个索引块存储的数据变多，从而减少io次数，提高效率
    * 更多关于B+Tree的结构介绍，另起一篇 todo[索引章节](https://www.cnblogs.com/hdk1993/p/5840599.html)    
* 复合索引
    * 最左匹配原则
        * 举例说明，目录第x章，第x节，如果直接第x节，无法精准定位数据
        * 会一直匹配匹配直到出现范围查询，停止匹配
            * 范围查询: >   <   between  like
    * mysql会对sql进行优化
        * ex：index(a,b,c) where c=1 and b=2 and a=3
        * mysql会对语句优化，使之变成 a=3 and b=2 and c=1
    * 如果缺失复合索引的第一列，无法使用索引  
* 永远不要在索引上做计算
    * select hash(a) = 1   ------>  select a = hash(1)
* 是否适合使用索引
    * count(distinct column)  /   count(column)的比值，越小，索引价值越高
* hash索引
    * 查询o(1)复杂度，偶尔hash冲突
    * 对范围查查询复杂度会退化为o(n)
    * innodb 不支持hash索引

* 使用explain结果，结合表结构，提升sql效率           


