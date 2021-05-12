package com.wing.lynne.leetcode.tree;


/**
 * 根据前序遍历和后序遍历初始化二叉树
 * https://leetcode-cn.com/contest/weekly-contest-98/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
 *
 * 前序遍历结果位pre
 * 后续遍历结果位post
 *
 * 前序遍历：根结点 ---> 左子树 ---> 右子树
 *
 * 中序遍历：左子树 ---> 根结点 ---> 右子树
 *
 * 后序遍历：左子树 ---> 右子树 ---> 根结点
 *
 * 深度优先遍历：上述三种均是
 *
 * 层次遍历：根节点 ---> 第一层节点 ---> 第二层节点 ---> ... ---> 最后一层节点
 *
 * 广度优先遍历：及按层遍历
 *
 * 输出中序遍历结果
 *
 * 先从前序遍历和后序遍历的数组中，找到相邻俩个元素完全一致的序列
 *
 *
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



