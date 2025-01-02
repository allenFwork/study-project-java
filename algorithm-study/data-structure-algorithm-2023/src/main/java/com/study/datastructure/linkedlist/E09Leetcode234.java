package com.study.datastructure.linkedlist;

/**
 * 判断回文链表
 */
public class E09Leetcode234 {
    /*
        步骤1. 找中间点的同时反转前半个链表
        步骤2. 反转后的前半个链表 与 中间点开始的后半个链表 逐一比较
                p1      p2
        1   2   2   1   null

        n1
        2   1

     */
    public boolean isPalindrome(ListNode head) {
        ListNode p1 = head; // 慢
        ListNode p2 = head; // 快
        ListNode newHead = null;  // 新头节点
        ListNode oldHead1 = head; // 旧头节点
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;

            // 反转链表
            oldHead1.next = newHead;
            newHead = oldHead1;
            oldHead1 = p1;
        }
        System.out.println(newHead);
        System.out.println(p1);

        if (p2 != null) { // 奇数节点，将中间节点向后移一位，此时的链表与之前反转后的链表比较
            p1 = p1.next;
        }

        while (newHead != null) {
            if (newHead.val != p1.val) {
                return false;
            }
            newHead = newHead.next;
            p1 = p1.next;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new E09Leetcode234().isPalindrome(ListNode.of(1, 2, 2, 1)));
        System.out.println(new E09Leetcode234().isPalindrome(ListNode.of(1, 2, 3, 2, 1)));
    }

    /*
                p1
                        p2
        1   2   3   2   1   null

     newHead
        2   1
     */

}
