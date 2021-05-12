package com.wing.lynne.leetcode.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * 获取最小祖先
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
 */
public class LowestAncestor {

    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(3);
        TreeNode treeNode2 = new TreeNode(5);
        TreeNode treeNode3 = new TreeNode(1);
        TreeNode treeNode4 = new TreeNode(6);
        TreeNode treeNode5 = new TreeNode(2);
        TreeNode treeNode6 = new TreeNode(0);
        TreeNode treeNode7 = new TreeNode(8);
        TreeNode treeNode8 = new TreeNode(7);
        TreeNode treeNode9 = new TreeNode(4);

        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;

        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;

        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;

        treeNode5.left = treeNode8;
        treeNode5.right = treeNode9;


        List<TreeNode> nodePath1 = findNodePath(treeNode1, treeNode2, new LinkedList<>());
        List<TreeNode> nodePath2 = findNodePath(treeNode1, treeNode9, new LinkedList<>());
        System.out.println(nodePath1);
        System.out.println(nodePath2);

        TreeNode treeNode = lowestCommonAncestor(treeNode1, treeNode2, treeNode9);
        System.out.println(treeNode);

    }


    /**
     * 解题思路，求给定的俩个点到根节点的路径，然后对比俩个路径，找到最小祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        List<TreeNode> firstNodePath = findNodePath(root, p, new LinkedList<>());
        List<TreeNode> secondNodePath = findNodePath(root, q, new LinkedList<>());

        int smallerListSize = firstNodePath.size() > secondNodePath.size() ? secondNodePath.size() : firstNodePath.size();

        int index = 0;
        for (int i = 1; i < smallerListSize; i++) {
            if (firstNodePath.get(i).val != secondNodePath.get(i).val) {
                index = i - 1;
                break;
            } else {
                index = i;
            }
        }

        return firstNodePath.get(index);
    }

    /**
     * 返回指定接到到根节点的路径
     *
     * @param tree
     * @param node
     * @return
     */
    public static LinkedList<TreeNode> findNodePath(TreeNode tree, TreeNode node, LinkedList<TreeNode> treeNodeList) {

        TreeNode left = tree.left;
        TreeNode right = tree.right;

        if (tree.val == node.val) {
            treeNodeList.add(node);
            return treeNodeList;
        }

        if (left != null) {
            int size = treeNodeList.size();
            treeNodeList = findNodePath(left, node, treeNodeList);
            if (size != treeNodeList.size()) {
                treeNodeList.addFirst(tree);
            }
        }

        if (right != null) {
            int size = treeNodeList.size();
            treeNodeList = findNodePath(right, node, treeNodeList);
            if (size != treeNodeList.size()) {
                treeNodeList.addFirst(tree);
            }
        }

        return treeNodeList;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }
}