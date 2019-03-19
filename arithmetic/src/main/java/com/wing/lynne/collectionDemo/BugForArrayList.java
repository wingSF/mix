package com.wing.lynne.collectionDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList使用不当，导致的bug
 *
 * todo
 *
 * ArrayList的add方法默认添加到末位
 * ArrayList的add(int index,Object obj)方法
 *  先确认数据容量能否放得下新元素
 *  实现过程为将底层的souce数组中的index到最后的元素复制到临时数组中
 *  然后将这部分数据在复制到source的index到最后的位置
 *  再将index位置的元素替换，完成插入动作
 * ArrayList的remove(int index,Object obj)方法的时间复杂度与上述的add(int index,Object obj)的相似
 *
 * 在批量插入大量元素的时候，应该手动调用ensureCapacity方法，一次扩容，避免多次扩容
 */
public class BugForArrayList {

    public static void main(String[] args) {

//        俩中list结构都会抛出异常
        List parentList = new ArrayList(2);
//        List parentList = new LinkedList();

        parentList.add("1");
        parentList.add("2");
        parentList.add("3");

        List childList = parentList.subList(1, 2);

        parentList.add("4");

        for (Object obj : childList) {
            System.out.println(obj);
        }

    }
}
