package com.study.datastructure.linkedlist;

/**
 * 反转链表
 */
public class E01Leetcode206 {
    // 方法1
    public ListNode reverseList1(ListNode oldHead) {
        ListNode newHead = null;
        ListNode p = oldHead;
        while (p != null) {
            newHead = new ListNode(p.val, newHead);
            p = p.next;
        }
        return newHead;
    }

    // 方法2
    public ListNode reverseList2(ListNode head) {
        List list1 = new List(head);
        List list2 = new List(null);
        while (true) {
            ListNode first = list1.removeFirst();
            if (first == null) {
                break;
            }
            list2.addFirst(first);
        }
        return list2.head;
    }

    static class List {
        ListNode head;

        public List(ListNode head) {
            this.head = head;
        }

        public void addFirst(ListNode first) {
            first.next = head;
            // 此题中，head就是第一个节点，head.next不是第一个节点。(非常容易出错，不同题目这里写法不一样)
            // 所以不能写成 head.next = first; 此处如果写成这样，会造成死循环，第一个节点指向自己了。
            head = first;
        }

        public ListNode removeFirst() {
            ListNode first = head;
            if (first != null) {
                head = first.next;
            }
            return first;
        }
    }

    // 方法3 - 递归
    public ListNode reverseList3(ListNode p) {
        if (p == null || p.next == null) {
            return p; // 最后节点
        }
        ListNode last = reverseList3(p.next);
        /*
         * 下面两行代码,含义是将相邻的两个节点逆序
         * 注意:
         * 1.不能写成 last.next = p;, 因为当 last节点是[5,4]的时候,此时变成了[5,3],不是[5,4,3]
         * 2.下面两个语句的顺序不能交换:

             当p是[4,5]时,last是[5],此时p.next.next就是last,即[5], 所以"p.next.next=p"就成了[5,4],
                 但是因为[4]后面绑着[5],就成了[5,4,5,...]逻辑结构了。所以得执行"p.next=null"，即[4]后面为null。
             当p是[3,4]时,last是[5,4], 此时p.next.next是null, 实质就是上一步的[4]后面绑定null；
                 "p.next.next=p"就是将当前节点[3,4]中的[3]绑定到[4]的后面,就成了[5,4,3,5,...]逻辑结构了。
                 所以得执行"p.next=null"，即[3]后面为null。
             ...

         */
        p.next.next = p;
        p.next = null;

        return last;
    }


    // 方法4 - 不断把 o2 头插入 n1
    public ListNode reverseList4(ListNode o1) {
        // 1. 空链表  2. 一个元素
        if (o1 == null || o1.next == null) {
            return o1;
        }
        ListNode o2 = o1.next;
        ListNode n1 = o1;
        while (o2 != null) {
            o1.next = o2.next;  // 2.
            o2.next = n1;       // 3. 这一步,对第一次循环而言 n1和o1是一样的,但是第二次循环后就不一致了,不能写成"o2.next=o1"
            n1 = o2;            // 4.
            o2 = o1.next;       // 5.
        }
        return n1;
    }

    // 方法5 - 不断把 o1 头插到 n1
    public ListNode reverseList(ListNode oldHead) {
        if (oldHead == null || oldHead.next == null) {
            return oldHead;
        }
        ListNode newHead = null;
        while (oldHead != null) {
            ListNode o2 = oldHead.next; // 2.
            oldHead.next = newHead; // 3.
            newHead = oldHead;      // 4.
            oldHead = o2;           // 4.
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        ListNode n1 = new E01Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

}
