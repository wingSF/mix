package com.wing.lynne.leetcode;

/**
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/42/
 * <p>
 * 删除链表的倒数第N个节点，并返回头结点
 */
public class RemoveNodeFromLinkedTable {

    public static void main(String[] args) {

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        int n = 2;
        removeNthFromEnd(node1, n);

        System.out.println(node1);

    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode headCopy = head;

        for (int i = 0; i < n; i++) {
            head = head.next;
        }

        int count = 0;

        if (head == null) {
            if (headCopy.next == null) {
                return null;
            } else {
                return headCopy.next;
            }
        } else {
            while (head.next != null) {
                count++;
                head = head.next;
            }

            head = headCopy;
            for (int i = 0; i < count; i++) {
                head = head.next;
            }

            head.next = head.next.next;
            return headCopy;
        }
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
