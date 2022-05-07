package com.study.class_basic_01;

import java.util.*;

/**
 * 比较器的使用
 */
public class Code_09_Comparator {

    public static class Student {
        public String name;
        public int id;
        public int age;

        public Student(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Name : " + this.name + ", Id : " + this.id + ", Age : " + this.age;
        }
    }

    /**
     * 自定义的比较器：比较的对象就是Student类型,根据Student的id大小进行排序
     * 按照id的值进行升序排序
     */
    public static class IdAscendingComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            /**
             * 返回的是负数,排序时,第一个数放在前面
             * 返回的是正数,排序时,第二个数放在前面
             * 返回的是0,两个对象在排序上大小是相等的
             */
            return o1.id - o2.id;
        }
    }

    /**
     * 按照id的值进行降序排序
     */
    public static class IdDescendingComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o2.id - o1.id;
        }
    }

    public static class AgeAscendingComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }

    public static class AgeDescendingComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o2.age - o1.age;
        }
    }

    public static void printStudents(Student[] students) {
        for (Student student : students) {
            System.out.println("Name : " + student.name + ", Id : " + student.id + ", Age : " + student.age);
        }
        System.out.println("===========================");
    }

    public static void main(String[] args) {
        Student student1 = new Student("A", 1, 23);
        Student student2 = new Student("B", 2, 21);
        Student student3 = new Student("C", 3, 22);

        Student[] students = new Student[]{student3, student2, student1};
        printStudents(students);

        Arrays.sort(students, new IdAscendingComparator());
        printStudents(students);

        Arrays.sort(students, new IdDescendingComparator());
        printStudents(students);

        Arrays.sort(students, new AgeAscendingComparator());
        printStudents(students);

        Arrays.sort(students, new AgeDescendingComparator());
        printStudents(students);

        /**
         * 优先级队列,就是堆的结构,创建时要传入比较器
         * 此时堆中存放Student对象时,会按照id进行排序,id小的放在最上面(前面)
         */
        PriorityQueue<Student> heap = new PriorityQueue<>(new IdAscendingComparator());
        // 将三个对象扔到堆中
        heap.add(student3);
        heap.add(student2);
        heap.add(student1);
        while (!heap.isEmpty()) {
            // 每次弹出堆的头部
            Student currentStudent = heap.poll();
            System.out.println(currentStudent);
        }

        TreeSet<Student> treeSet = new TreeSet(new IdAscendingComparator());

    }

}
