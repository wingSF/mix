# InnoDB数据页结构
* 页
    * InnoDB管理存储空间的基本单位
    * 一般大小为16kb
    * 构成
        * File Header 38字节
            * 页的一些通用信息
        * Page Header 56字节
            * 数据页专有的一些信息
        * Infimum + supermum 26字节
            * 俩个虚拟的行记录
            * 最大 & 最小 记录
        * User Records 大小不确定
            * 实际存储的行记录内容
        * Free Space 大小不确定
            * 页中尚未使用的空间
        * Page Directory 大小不确定
            * 页中的某些记录的相对位置
        * File Tailer 8字节
            * 校验页是否完整
* 记录头信息
    * delete_mask
        * 0表示记录没有被删除，1代表记录被删除掉了
        * 记录被用户删除后，并不会立即从磁盘上移除
            * 移除删除记录之后，把其他记录在磁盘上重新排列需要性能消耗
            * 只是做一个删除标记，所有被删除的记录会组成一个垃圾链表
            * 这些链表占用的空间称之为可重用空间
            * 后续新纪录插入到表中，可能把这些被删除记录占用的存储空间覆盖掉
    * heap_no
        * 当前记录在本页中的位置
        * heap_no值为0和1代表最小记录和最大记录
            * 最大最小记录内容固定，不存储在user_records内
    * record_type
        * 0表示普通记录
        * 1表示B+树非叶子节点记录
        * 2最小记录，3最大记录
    * next_record
        * 从当前记录的真实数据到下一条记录的 `真实数据`的地址偏移量（链表）
            * 这个位置向前是头信息，向后是数据信息
            * 头信息后面的列信息，都是逆序排列
            * 所以从这个位置开始读，可以将靠前的字段和他们对应的字段长度信息在内存中距离更近
        * 下一条记录于插入顺序无关，是按照主键从小到大排序的
        * 最小和最大，都已经被定义好了     
    * Page Directory
        * 在页中检索数据的时候，如果使用链表，从头遍历到尾，不太科学
        * 将数据分组
            * mysql规定
                * 最小记录所在分组，只能有1条记录
                * 最大记录所在分组，只能有1-8条记录
                * 其余分组，条数范围是4-8
                * 每个组的最大记录的记录头信息中的n_owned字段记录该组的记录条数
            * 模拟分组过程
                * 最小记录一个组，最大记录一个组，开始插入数据
                * 插入7条数据，最小记录1个，最大记录8个
                * 再插入一条
                    * 最大记录组裂解，新的组4条，最大记录组5条
                * 依次类推
        * 将分组后的最大记录的地址偏移量单独提取出来，放到Page Directory中保存起来
            * 检索数据时，不再直接检索链表，先二分确定分组，在使用链表特性遍历
            * 每个数据称之为SLOTS，槽
    * Page Header
        * 56字节，存储数据页中存储的记录的各种状态信息，以下字段无说明，都是2字节
            * PAGE_N_DIR_SLOTS 
                * 在页目录中的槽数量
            * PAGE_HEAP_TOP
                * 还未使用的空间最小地址，该地址之后是Free Space
            * PAGE_N_HEAP
                * 本页中的记录的数量，包括最大&最小&删除的记录
            * PAGE_FREE
                * 第一个已经标记为删除的记录地址，各个删除的记录通过next_record组成单链表
            * PAGE_GARBAGE
                * 已删除记录占用的字节数
            * PAGE_LAST_INSERT
                * 最后插入记录的位置
            * PAGE_DIRECTION
                * 当新插入的数据的主键比上次插入主键id大，方向为右边，反之为左边
            * PAGE_N_DIRECTION
                * 当连续插入的方向相同时，InnoDB会把沿着同一个方向插入记录的条数记下来，保存在该字段
                * 如果插入方向改变的话，这个状态值会被清零
            * PAGE_N_RECS
                * 该页中的记录的数量，不包括最大 & 最小 & 被删除的记录
            * PAGE_LEVEL
                * 当前页在B+树中所处的层级
            > 2字节 字段 10个
            * PAGE_MAX_TRX_ID   
                * 修改当前页的最大事务ID，该值仅在耳机索引中定义
                * 8字节
            * PAGE_INDEX_ID
                * 索引ID，表示当前页属于哪个索引
                * 8字节
            > 8字节 字段 2个
            * PAGE_BTR_SEG_LEAF
                * B+树叶子段的头部信息，仅在B+树的Root页定义
                * 10字节
            * PAGE_BTR_SEG_TOP
                * B+树非叶子段的头部信息，仅在B+树的Root页定义
                * 10字节
            > 10字节 字段 2个  
            2*10 + 8*2 + 10*2 = 56
    * File Header
        * 各种页都通用的信息 38字节
            * FUL_PAGE_SPACE_OR_CHKSUM
                * 页的校验和
                * 4字节
            * FIL_PAGE_OFFSET
                * 页号，InnoDB通过页号来唯一定位一个页
                * 4字节
            * FIL_PAGE_PREV
                * 上一个页的页号
                * 4字节
            * FIL_PAGE_NEXT
                * 下一个页的页号
                * 
            > FIL_PAGE_PREV & FIL_PAGE_NEXT 可以将多页，串联起来，构成双向链表  
            这也多个页可以物理上不连续
            * FIL_PAGE_LSN
                * log sequence number
                * 页面被最后修改时对应的日志序列位置
                * 8字节
            * FIL_PAGE_TYPE
                * 页的类型
                * 2字节
                * FIL_PAGE_TYPE_ALLOCATED
                    * 0x0000，最新分配还没使用
                * FIL_PAGE_UNDO_LOG
                    * 0x0002，undo日志页
                * FIL_PAGE_INODE
                    * 0x0003，段信息节点
                * FIL_PAGE_IBUF_FREE_LIST
                    * 0x0004，Insert Buffer空闲列表
                * FIL_PAGE_IBUF_BITMAP
                    * 0x0005，Insert Buffer位图
                * FIL_PAGE_TYPE_SYS
                    * 0x0006，系统页
                * FIL_PAGE_TYPE_TRX_SYS
                    * 0x0007，事务系统数据
                * FIL_PAGE_TYPE_FSP_HDR
                    * 0x0008，表空间头部信息
                * FIL_PAGE_TYPE_XDES
                    * 0x0009，扩展描述页
                * FIL_PAGE_TYPE_BLOB
                    * 0x000A，溢出页
                * FIL_PAGE_INDEX
                    * 0x45BF，索引页，也就是常说的数据页
            * FIL_PAGE_TILE_FLUSH_LSN
                * 仅在系统表空间的一个页中定义，代表文件至少被刷新到了对应的LSN值
                * 8字节
            * FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID
                * 页属于哪个表空间
                * 4字节
    * File Trailer
        * 设计目的
            * 校验页刷新到磁盘是否成功
        * 原理
            * File Header中页有一个校验和字段
            * 同步过程中，先同步File Header中的校验和字段
            * 同步数据
            * 校验和同步到File Trailer
            * 如果写一半中断了，则首尾校验和不一致
        * 8字节
            * 前4字节，页的校验和
            * 后4字节，页面被修改是对应的日志序列位置(LSN)  
                                