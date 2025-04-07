package com.study.datastructure.binarytree;

import com.study.datastructure.stack.LinkedListStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树前序遍历(值,左,右) （根,左,右）
 */
public class E01Leetcode144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        // 使用jdk的LinkedList作为站，实现记录父子关系，由子到父（栈弹出来时的顺序表示）
        LinkedList<TreeNode> stack = new LinkedList<>();

        TreeNode curr = root; // 代表当前节点
        TreeNode pop = null; // 最近一次弹栈的元素

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                // 待处理左子树
                result.add(curr.val);
                curr = curr.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right == null) {       // 没有右子树，即右子树都处理完了
                    pop = stack.pop();
                } else if (peek.right == pop) { // 右子树处理完成
                    pop = stack.pop();
                }
                // 待处理右子树
                else {
                    curr = peek.right;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        /*TreeNode root = new TreeNode(
                new TreeNode(2), 1, new TreeNode(3)
        );*/

        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(4), 2, null),
                1,
                new TreeNode(new TreeNode(5), 3, new TreeNode(6))
        );
        System.out.println(new E01Leetcode144().preorderTraversal(root));

        LinkedListStack<TreeNode> stack = new LinkedListStack<>();

        TreeNode curr = root; // 代表当前节点
        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                colorPrintln("去: " + curr.val, 31);
                stack.push(curr); // 压入栈，为了记住回来的路
                curr = curr.left;
            } else {
                TreeNode pop = stack.pop();
                colorPrintln("回: " + pop.val, 34);
                curr = pop.right;
            }
        }
    }

    /*
        31 红
        32 黄
        33 橙
        34 蓝
        35 紫
        36 绿
     */
    public static void colorPrintln(String origin, int color) {
        System.out.printf("\033[%dm%s\033[0m%n", color, origin);
    }
}
