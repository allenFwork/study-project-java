package com.study;

import com.study.datastructure.linkedlist.ListNode;

public class TempTest {

    public static void main(String[] args) {

//        ListNode node1 = new ListNode(5, null);
//        ListNode node2 = new ListNode(4, node1);
        ListNode node3 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node3);
        ListNode node5 = new ListNode(8, node4);
        ListNode node6 = new ListNode(1, node5);
        ListNode node7 = new ListNode(4, node6);

        ListNode node2_2 = new ListNode(5, null);
        ListNode node3_2 = new ListNode(4, node2_2);
        ListNode node4_2 = new ListNode(8, node3_2);
        ListNode node5_2 = new ListNode(1, node4_2);
        ListNode node6_2 = new ListNode(6, node5_2);
        ListNode node7_2 = new ListNode(5, node6_2);
//        node4.next = node7;
        ListNode node = getIntersectionNode(node7, node7_2);
        System.out.println(node);
    }


    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != null && p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        int count = 0;
        boolean leftFlag = false;
        boolean rightFlag = false;
        boolean flag = false;
        if (p1 == null) {
            rightFlag = true;
            flag = true;
            while (p2 != null) {
                p2 = p2.next;
                count++;
            }
        }
        if (p2 == null && !flag) {
            leftFlag = true;
            while (p1 != null) {
                p1 = p1.next;
                count++;
            }
        }
        p1 = headA;
        p2 = headB;
        if (leftFlag) {
            for (int i = 0; i < count && p1 != null; i++) {
                p1 = p1.next;
            }
        }
        if (rightFlag) {
            for (int i = 0; i < count && p2 != null; i++) {
                p2 = p2.next;
            }
        }
        while (p1 != null && p2 != null && p1.val != p2.val ) {
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1 != null && p2 != null && p1.val == p2.val) {
            return p1;
        } else {
            return null;
        }
    }
}
