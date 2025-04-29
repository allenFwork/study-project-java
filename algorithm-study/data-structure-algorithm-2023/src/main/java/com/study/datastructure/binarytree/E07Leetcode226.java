package com.study.datastructure.binarytree;

/**
 * 翻转二叉树
 */
public class E07Leetcode226 {
    public TreeNode invertTree(TreeNode root) {
        reverse(root);
        return root;
    }

    private static void reverse(TreeNode node) {
        if (node == null) {
            return;
        }
        TreeNode t = node.left;
        node.left = node.right;
        node.right = t;

        reverse(node.left);
        reverse(node.right);
    }
}
