package com.wing.lynne.collectionDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList使用不当，导致的bug
 *
 * 问题描述
 *      通过一个ArrayList类型的parentList对象，调用sublist方法获取一个childList，此时如果对parentList做结构性的改变，那么childList的方法会抛出异常
 * 问题分析
 *      通过subList方法的注释可知，subList返回的结果只是parentList的一个view(视图)，对于parentList任何结构性的改变，将导致childList变成undifined
 *
 *      Returns a view of the portion of this list between the specified <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive
 *
 *      The semantics of the list returned by this method become undefined if the backing list (i.e., this list) is <i>structurally modified</i> in
 *      any way other than via the returned list.
 *      (Structural modifications are those that change the size of this list, or otherwise perturb it in such a fashion
 *      that iterations in progress may yield incorrect results.)
 * 源码分析
 *      先弄明白一个语法，类名.this.
 *      在java中，this常常用来表示当前类的对象，当类出现内部类的时候，为了在内部类的代码中引用外用类的成员变量，需要使用类名.this的语法
 *      在ArrayList#sublist中，会new 一个SubList，这个类是ArrayList的一个内部类，在构造方法中使用了this.modCount = ArrayList.this.modCount
 *      从此埋下了这个bug的根源
 *      SubList有一个parent的成员变量，在构造时会被赋值为parentList对象，在之后的所有方法中，相当于对parentList直接做了变更.
 *      当sublist做结构性改变的时候，会调用parent的方法做增删，然后将parent的modCount赋值给内部类对象
 *      当parentList做结构性改变的时候，没有对childList对象做modCount变更，在childList内部有一个checkForComodification方法，该方法一旦被调用 parentList.modCount != childList.modCount，此时抛出ConcurrentModificationException
 *
 * ArrayList使用建议
 *      在批量插入大量元素的时候，应该手动调用ensureCapacity方法，一次扩容，避免多次扩容
 *
 * ArrayList的add方法默认添加到末位
 *      ArrayList的add(int index,Object obj)方法
 *      先确认数据容量能否放得下新元素
 *      实现过程为将底层的souce数组中的index到最后的元素复制到临时数组中
 *      然后将这部分数据在复制到source的index到最后的位置
 *      再将index位置的元素替换，完成插入动作
 *
 * ArrayList的remove(int index,Object obj)方法的时间复杂度与上述的add(int index,Object obj)的相似
 *
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
