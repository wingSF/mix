package com.wing.lynne.leetcode;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list-ii/
 *
 * 链表反转
 *
 * 写在最前面，这个题目本身具有一定的诱导性，最初一直在尝试通过一次遍历把链表反转过来，被反转这个词带走远了。
 * 反转只是强调效果～
 *
 * 写在最后面，实际上链表的反转，使用了额外空间，是重新创建节点，重新创建的链表，效果上是反转的
 */
public class ReverseLinkedList {


    public static void main(String[] args) {

//        ListNode node5 = new ListNode(5);
//        ListNode node4 = new ListNode(4, node5);
//        ListNode node3 = new ListNode(3, node4);
//        ListNode node2 = new ListNode(2, node3);
//        ListNode node1 = new ListNode(1, node2);

        ListNode node2 = new ListNode(5, null);
        ListNode node1 = new ListNode(3, node2);


        printListNode(node1);

        ListNode listNode = reverseBetween(node1, 1, 2);
        printListNode(node1);
        printListNode(listNode);
    }


    public static ListNode reverseBetween(ListNode head, int left, int right) {

        //节点备份，用于返回值
        ListNode headBak = head;

        //边界判断
        if (head == null || head.next == null || left == right) {
            return head;
        }

        int count = 1;

        //找到反转的前一个位置，如果left==1，该值是null，返回时需注意
        ListNode sepNode = null;
        while (true) {
            if (count == left) {
                break;
            } else {
                sepNode = head;
                head = head.next;
                count++;
            }
        }

        //记录新链表
        ListNode nextNode = null;
        //记录新链表的最后一个节点
        ListNode lastNode = null;
        while (true) {
            //反转
            if (nextNode == null) {
                nextNode = new ListNode(head.val, nextNode);
                lastNode = nextNode;
            } else {
                nextNode = new ListNode(head.val, nextNode);
            }

            head = head.next;
            if (count == right) {
                break;
            } else {
                count++;
            }
        }

        //重新串接链表
        if (sepNode != null) {
            sepNode.next = nextNode;
        }
        lastNode.next = head;

        //根据sepNode是否位null，返回不同的值
        return sepNode == null ? nextNode : headBak;
    }

    public static void printListNode(ListNode node) {
        StringBuilder stringBuilder = new StringBuilder();
        while (node != null) {
            stringBuilder.append(node.val);
            node = node.next;
            if (node != null) {
                stringBuilder.append("-->");
            }
        }
        System.out.println(stringBuilder.toString());

    }

}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}