package com.wing.lynne.leetcode.linkedList;

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

        System.out.println("before : " + node1);
        System.out.println("going to remove 倒数第" + n + "个");

        removeNthFromEnd(node1, n);
//        removeNthFromEnd1(node1, n);

        System.out.println("after : " + node1);

    }

    /**
     * by 肖冉 双指针
     * @param node1
     * @param n
     * @return
     */
    private static ListNode removeNthFromEnd1(ListNode node1, int n) {

        long startTime = System.nanoTime();

        ListNode start = node1;
        ListNode end = node1;

        for (int i = 0; i < n; i++) {
            end = end.next;
        }

        while (true) {

            if (end.next == null) {
                start.next = start.next.next;
                long endTime = System.nanoTime();
                System.out.println("removeNthFromEnd1 耗时" + (endTime - startTime));
                return node1;
            } else {
                start = start.next;
                end = end.next;
            }

        }


    }

    /**
     * by me 对链表进行了俩次遍历，且for循环判断耗时较长
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {

        long startTime = System.nanoTime();
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

            long endTime = System.nanoTime();
            System.out.println("removeNthFromEnd 耗时" + (endTime - startTime));
            return headCopy;
        }
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return val + (next == null ? "" : "->" + next.toString());
        }
    }

}
