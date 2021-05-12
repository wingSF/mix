package com.wing.lynne.leetcode.linkedList;

import com.wing.lynne.leetcode.linkedList.ListNode;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 *
 * 要求
 *      时间复杂度O(n)   可以遍历多次，但不能嵌套遍历，对于多个链表通常使用多指针法一起遍历
 *      空间复杂度O(1)   这个基本判断不能对链表中的元素进行拷贝，只能存当前节点的数据
 *
 * 思路
 *      先将链表遍历一边，确定长度
 *      由题意可知，如果链表相交，相交的部分完全一致。所以遍历长的链表，将俩个链表长度变成一致的
 *      同时遍历俩个链表到结尾，如果值一样保存到临时变量中，继续遍历。如果值不一样，将临时变量清空，继续遍历。
 */
public class IntersectionOfTwoLinkedLists {

    public static void main(String[] args) {

        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(8, node4);
        ListNode node2 = new ListNode(1, node3);

        ListNode node_b_1 = new ListNode(4, node2);

        ListNode node_a_2 = new ListNode(6, node2);
        ListNode node_a_1 = new ListNode(5, node_a_2);

        ListNode intersectionNode = getIntersectionNode(node_b_1, node_a_1);

        System.out.println(intersectionNode);

    }


    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        /**
         * 第一步，求俩个链表的长度
         */
        int lengthA = 0;
        int lengthB = 0;
        ListNode tempA = headA;
        ListNode tempB = headB;
        while (tempA != null || tempB != null) {
            if (tempA != null) {
                lengthA++;
                tempA = tempA.next;
            }
            if (tempB != null) {
                lengthB++;
                tempB = tempB.next;
            }
        }

        //第二步，调整长度
        int diff = Math.abs(lengthA - lengthB);
        int move = 0;
        if (lengthA > lengthB) {
            while (move != diff) {
                headA = headA.next;
                move++;
            }
        } else if (lengthA < lengthB) {
            while (move != diff) {
                headB = headB.next;
                move++;
            }
        }

        //第三步骤，开始遍历找交点
        ListNode resultNode = headA;

        while (headA != null) {
            headA = headA.next;
            headB = headB.next;
            if (headA.val == headB.val) {
                if (resultNode == null) {
                    resultNode = headA;
                }
                continue;
            } else {
                resultNode = null;
            }

        }

        return resultNode;
    }

}

