package com.study.Stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest2 {

    public static void main(String[] args) {

        List<Student> list = new ArrayList<>();
        list.add(new Student(1, Grade.FIRST, 40));
        list.add(new Student(2, Grade.FIRST, 60));
        list.add(new Student(3, Grade.FIRST, 30));
        list.add(new Student(4, Grade.SECOND, 80));
        list.add(new Student(5, Grade.THIRD, 100));
        list.add(new Student(6, Grade.FIRST, 40));
        list.add(new Student(7, Grade.FIRST, 70));
        list.add(new Student(7, Grade.SECOND, 10));
        list.add(new Student(7, Grade.SECOND, 60));

        Set<Integer> scoreList = list.stream().map(Student::getScore).collect(Collectors.toSet());
        for (Integer integer : scoreList) {
            integer = 100;
        }
//        scoreList.forEach(x -> x = 100);
        System.out.println(list);

        System.out.println("================= 按照一个字段的值进行排序 =================");
        List<Student> studentList1 = list.stream().sorted(Comparator.comparing(x -> x.getId())).collect(Collectors.toList());
        for (int i = 0; i < studentList1.size(); i++) {
            System.out.println(studentList1.get(i).toString());
        }

        System.out.println("================= 按照多个字段的值进行排序 =================");
        List<Student> studentList2 = list.stream().sorted(Comparator.comparing(Student::getGrade).thenComparing(Student::getId)).collect(Collectors.toList());
        for (int i = 0; i < studentList2.size(); i++) {
            System.out.println(studentList2.get(i));
        }
        list.stream().sorted(Comparator.comparing(x -> x.getId()));

    }

}
