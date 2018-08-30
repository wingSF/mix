package com.wing.lynne;


/**
 * 根据前序遍历和后序遍历初始化二叉树
 * https://leetcode-cn.com/contest/weekly-contest-98/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
 */
public class CreateTree {

    public TreeNode constructFromPrePost(int[] pre, int[] post) {

        TreeNode tree = new TreeNode(pre[0]);




        return tree;
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}



