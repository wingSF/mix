package com.wing.lynne;

/**
 * 给订一个树形结构，求距离 某个节点 最远的 叶子节点  的距离
 */
public class FindLongestNode {

    public static void main(String[] args) {

        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);

        node1.left = node2;
        node1.right = node3;

        node2.left = node4;
        node2.right = node5;

        node5.left = node6;

        node6.right = node7;

        String longestPath = findLongestPath(node1, "");

        String nodePath = findNodePath(node1, node4, "");

        char[] longestPathCharArray = longestPath.toCharArray();
        int longestPathCharArrayLength = longestPathCharArray.length;

        char[] nodePathCharArray = nodePath.toCharArray();
        int smallLength = nodePathCharArray.length;


        int index = 1;
        for (int i = 1; i < smallLength; i++) {

            if (longestPathCharArray[i] != nodePathCharArray[i]) {
                index = i;
                break;
            }
        }

        int length = longestPathCharArrayLength + smallLength - 2 * index;

        //最深节点的路径数组
        System.out.println(longestPathCharArray);
        //选定节点的路径数组
        System.out.println(nodePathCharArray);
        //选定节点与最深节点的距离
        System.out.println("距离 = " + length);

    }

    /**
     * 寻找树形结构中的，最远节点的路径
     *
     * @param tree
     * @param path
     * @return
     */
    public static String findLongestPath(TreeNode tree, String path) {

        if (tree != null) {
            path += tree.val;
        } else {
            return path;
        }

        TreeNode left = tree.left;
        String leftPath = path;

        TreeNode right = tree.right;
        String rightPath = path;

        if (left != null) {
            leftPath = findLongestPath(left, leftPath);
        }

        if (right != null) {
            rightPath = findLongestPath(right, rightPath);
        }


        return leftPath.length() > rightPath.length() ? leftPath : rightPath;
    }


    /**
     * 返回指定接到到根节点的路径
     *
     * @param tree
     * @param node
     * @param path
     * @return
     */
    public static String findNodePath(TreeNode tree, TreeNode node, String path) {

        TreeNode left = tree.left;
        TreeNode right = tree.right;

        if (tree.val == node.val) {
            path = tree.val + path;
            return path;
        }

        if (left != null) {
            String temp = path;
            path = findNodePath(left, node, path);
            if (!temp.equals(path)) {
                path = tree.val + path;
            }
        }

        if (right != null) {
            String temp = path;
            path = findNodePath(right, node, path);
            if (!temp.equals(path)) {
                path += tree.val + path;
            }
        }


        return path;
    }

    /**
     * 二叉树实体类
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}


