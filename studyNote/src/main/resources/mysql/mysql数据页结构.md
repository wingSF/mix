# mysql 数据页
* user record & free space
    * user record用来存放我们数据的地方，存放的时候，会指定一定的格式，这个格式叫行格式(ROW_FORMAT)
    * 页最初创建的时候，是没有user record空间的，只有free space
    * 之后每插入记录，就用free space中拿一些空间，最后free space空间为0，申请下一个页面
* 行格式
    * 记录的真实信息
        * 每列的值相邻存储
        * 会追加隐藏列，如transaction_id,roll_pointer
    * 记录的额外信息
        * 这部分数据会拼接在数据真心信息的前面
        * 与记录的真实信息一起组成了实际存储的数据
        * 有以下三个部分组成
            * 变成字段长度列表
            * null值列表
            * 记录头信息
                * 预留位 
                    * 2位
                * delete_mask
                    * 标注记录是否被删除
                    * 数据删除的步骤，在下面next_record中描述
                * min_rec_mask 
                    * B+树的每层非叶子节点中的最小记录都会添加该标记
                * n_owned
                * heap_no
                    * 数据记录在本页中的位置
                    * 从2开始，0和1，被用来表示最小|最大记录
                    * 最小&最大记录，每页都有，不存在user_record中，存在Infimum + Supremum区域中
                * record_type
                    * 0 普通记录
                    * 1 B+树非叶子节点记录
                    * 2 最小记录
                    * 3 最大记录
                * next_record
                    * 指向 按序排列 的下一条数据的 真实数据区域 的开始位置
                        * 按序排列
                            * 按照主键的顺序
                        * 真实数据区域
                            * 记录头信息结束的位置
                            * 第一列数据存储的头位置
                    * 最小记录的next_record指向第一条user_record
                    * 最后一个user_record的next_record指向最大记录
                    * 最大记录的该字段为0 
                    * 删除数据的过程
                        * 先修改delete_mask
                        * 将上一条记录的next_record修改为当前记录保存的next_record的值
                        * 将当前记录加入垃圾链表
                        * 此时记录还存放在原来的位置上
                        * 当再次插入上次被删掉的数据(主键id一致)时，会重用原来的记录位置