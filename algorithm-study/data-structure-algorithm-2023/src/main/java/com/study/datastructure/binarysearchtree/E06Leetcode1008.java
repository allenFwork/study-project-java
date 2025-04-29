package com.study.datastructure.binarysearchtree;


/**
 * <h3>根据前序遍历构造二叉搜索树</h3>
 * 题目说明
 * <ol>
 *     <li>preorder 长度 >=1</li>
 *     <li>preorder 没有重复值</li>
 * </ol>
 */
public class E06Leetcode1008 {

    public TreeNode bstFromPreorder(int[] preorder) {
//        TreeNode root = insert(null, preorder[0]);
//        for (int i = 1; i < preorder.length; i++) {
//            insert(root, preorder[i]);
//        }

        TreeNode root = insert3(preorder, 0, preorder.length - 1);
        return root;
    }

    private TreeNode insert(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }
        if (val < node.val) {
            node.left = insert(node.left, val);
        } else if (node.val < val) {
            node.right = insert(node.right, val);
        }
        return node;
    }

    /*
        依次处理 preorder 中每个值，返回创建好的节点或null1
        1. 如果超过上限，返回 null 作为孩子返回
        2. 如果没超过上限，创建节点，并设置其左右孩子
            左右孩子完整后返回
     */
    int i = 0;

    private TreeNode insert2(int[] preorder, int max) {
        if (i == preorder.length) {
            return null;
        }
        int value = preorder[i];
        if (value > max) {
            return null;
        }
        TreeNode node = new TreeNode(value);
        i++;
        node.left = insert2(preorder, value);
        node.right = insert2(preorder, max);
        return node;
    }

    // 分治思想
    private TreeNode insert3(int[] preorder, int start, int end) {
        if (start > end) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[start]);
        int index = start + 1;
        // 必须判断 index < end， 否则最后一个节点处理的时候, 会下标越界
        while (index < end && preorder[start] > preorder[index]) {
            index++;
        }
        root.left = insert3(preorder, start + 1, index - 1);
        root.right = insert3(preorder, index, end);
        return root;
    }

    public static void main(String[] args) {
        /*
                8
               / \
              5   10
             / \   \
            1   7  12
         */
        TreeNode t1 = new E06Leetcode1008().bstFromPreorder(new int[]{8, 5, 1, 7, 10, 12});
//        TreeNode t1 = new E06Leetcode1008().bstFromPreorder(new int[]{8, 5, 7});
        TreeNode t2 = new TreeNode(8, new TreeNode(5, new TreeNode(1), new TreeNode(7)), new TreeNode(10, null, new TreeNode(12)));
        System.out.println(isSameTree(t1, t2));
    }

    public static boolean isSameTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.val != t2.val) {
            return false;
        }
        return isSameTree(t1.left, t2.left) && isSameTree(t1.right, t2.right);
    }
}
