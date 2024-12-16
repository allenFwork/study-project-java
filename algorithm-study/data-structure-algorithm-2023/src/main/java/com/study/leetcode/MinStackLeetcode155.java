package com.study.leetcode;

import java.util.LinkedList;

@SuppressWarnings("all")
public class MinStackLeetcode155 {
    static class MinStack {
        LinkedList<Integer> stack = new LinkedList<>();
        LinkedList<Integer> min = new LinkedList<>();

        public MinStack() {
            min.push(Integer.MAX_VALUE);
        }

        public void push(int val) {
            stack.push(val);
            min.push(Math.min(val, min.peek()));
        }

        public void pop() {
            if (stack.isEmpty()) {
                return;
            }
            stack.pop();
            min.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min.peek();
        }
    }

    static class MinStack2 {
        record Data(int val, int min) {

        }

        LinkedList<Data> stack = new LinkedList<>();
        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(new Data(val, val));
            } else {
                stack.push(new Data(val, Math.min(stack.peek().min, val)));
            }
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek().val;
        }

        public int getMin() {
            return stack.peek().min;
        }
    }

    public static void main(String[] args) {
        MinStack stack2 = new MinStack();
        stack2.push(-2);
        stack2.push(0);
        stack2.push(-3);
        System.out.println(stack2.getMin()); // -3
        stack2.pop();
        System.out.println(stack2.top()); // 0
        System.out.println(stack2.getMin()); // -2
    }
}
