package com.wing.lynne.leetcode;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 *
 * 递归方式，总还是有弊端的呦
 * 边界判断，可以提升leetcode网站，测试用例的执行时间，但实际中能否提升，有待考究
 *
 */
public class TwoNumber {

    public static void main(String[] args) {

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node7 = new ListNode(7);
        ListNode node8 = new ListNode(8);
        ListNode node9 = new ListNode(9);


        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        node6.next = node7;
        node7.next = node8;
        node8.next = node9;

        System.out.println(node1);
        System.out.println(node6);

        System.out.println(addTwoNumber(node1, node6));
//        System.out.println(addTwoNumber(node5, node5));
    }

    public static ListNode addTwoNumber(ListNode node1, ListNode node2) {
        return addTwoNumber(node1, node2, 0, new ListNode());
    }

    public static ListNode addTwoNumber(ListNode node1, ListNode node2, int higher, ListNode result) {

        if (node1 == null) {
            node1 = new ListNode(0);
        }

        if (node2 == null) {
            node2 = new ListNode(0);
        }

        int temp = node1.val + node2.val + higher;
        higher = temp / 10;
        result.val = temp % 10;
        if (node1.next == null && node2.next == null && higher == 0) {
            return result;
        }

        ListNode resultNext = new ListNode();
        result.next = resultNext;

        addTwoNumber(node1.next, node2.next, higher, resultNext);

        return result;
    }

    private static class ListNode {
        int val;
        public ListNode next;

        public ListNode() {

        }

        public ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return val + (next == null ? "" : "->" + next);
        }
    }

}
