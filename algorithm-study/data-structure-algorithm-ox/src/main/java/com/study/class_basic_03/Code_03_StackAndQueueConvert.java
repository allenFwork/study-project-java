package com.study.class_basic_03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 问题3：
 * 1.如何仅用队列结构实现栈结构
 * 2.如何仅用栈结构实现队列结构
 */
public class Code_03_StackAndQueueConvert {

    /**
     * 通过两个栈实现队列结构
     */
    public static class TwoStacksQueue {
        // 用来向队列中加入数据的栈
        private Stack<Integer> stackPush;
        // 用于从队列中取出数据的栈
        private Stack<Integer> stackPop;

        public TwoStacksQueue() {
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();
        }

        /**
         * 向队列中添加数据,直接放入到 stackPush对应的栈中，依次放入即可
         */
        public void push(int pushInt) {
            stackPush.push(pushInt);
        }

        /**
         * 从队列中取出数据(先进后出,取出最后放入的数据)：
         * 1.判断stackPop对应用来取数据的栈中是否有数据
         * 2.如果stackPop有数据，直接从中拿数据(栈结构,拿的是栈顶的数据,即最先放入到队列中的数据)
         * 3.如果stackPop没有数据（必须是stackPop没有数据的情况下）,从stackPush中依次弹出数据放入到stackPop中
         * （此时的栈顶到栈底的元素顺序就是放入队列的先后顺序,如果取数据,就满足队列的先进先出）
         */
        public int poll() {
            if (stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty!");
            } else if (stackPop.empty()) {
                while (!stackPush.empty()) {
                    stackPop.push(stackPush.pop());
                }
            }
            return stackPop.pop();
        }

        public int peek() {
            if (stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty!");
            } else if (stackPop.empty()) {
                while (!stackPush.empty()) {
                    stackPop.push(stackPush.pop());
                }
            }
            return stackPop.peek();
        }
    }

    /**
     * 通过两个队列实现栈结构
     */
    public static class TwoQueuesStack {
        // queue引用指向的队列存放着具体的数据
        private Queue<Integer> queue;
        // help引用指向帮助队列,用于中间逻辑处理
        private Queue<Integer> help;

        public TwoQueuesStack() {
            queue = new LinkedList<Integer>();
            help = new LinkedList<Integer>();
        }

        /**
         * 压入向栈中压入元素：向queue指向的队列最后一位添加一个元素
         */
        public void push(int pushInt) {
            queue.add(pushInt);
        }

        public int peek() {
            if (queue.isEmpty()) {
                throw new RuntimeException("Stack is empty!");
            }
            while (queue.size() != 1) {
                help.add(queue.poll());
            }
            int res = queue.poll();
            // 将从队列中取出的最后一位放回到队列中
            help.add(res);
            swap();
            return res;
        }

        /**
         * 从栈中取出元素,弹出元素：
         * 1.判断queue指向的队列中是否存在元素,没有直接报错
         * 2.判断queue指向的队列中元素的个数是否超过一个，没有就直接返回该元素，并清除掉queue队列中的该元素
         * 3.queue指向的队列中元素的个数是超过一个,就一个一个地从queue指向的队列中取出元素，并将它们依次放入到help之乡的队列中,
         * 直到queue指向的队列中只剩下一个元素。
         * 4.从queue指向的队列中取出元素返回
         * 5.最后将 queue 与 help 的引用互换,此时queue指向的队列就存放着原来的队列中的元素，只是少了队列末尾的那个元素
         */
        public int pop() {
            if (queue.isEmpty()) {
                throw new RuntimeException("Stack is empty!");
            }
            while (queue.size() > 1) {
                help.add(queue.poll());
            }
            int res = queue.poll();
            swap();
            return res;
        }

        // 改变两个队列的引用关系
        private void swap() {
            Queue<Integer> tmp = help;
            help = queue;
            queue = tmp;
        }

    }

    public static void main(String[] args) {

    }

}
