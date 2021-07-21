package com.study.Stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {

        final Collection<Student> students = Arrays.asList(
                new Student(5, Grade.FIRST, 40),
                new Student(1, Grade.FIRST, 60),
                new Student(4, Grade.FIRST, 30),
                new Student(2, Grade.SECOND, 80),
                new Student(3, Grade.THIRD, 100)
        );

        /*---------------------------- 集合处理：原始方法（开始） -------------------------------*/
//        // 1.遍历获取一年级学生
//        List<Student> gradeOneStudents = new ArrayList<>();
//        for (Student student : students) {
//            if (Grade.FIRST.equals(student.getGrade())) {
//                gradeOneStudents.add(student);
//            }
//        }
//        // 2.排序
//        Collections.sort(gradeOneStudents, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o2.getScore().compareTo(o1.getScore());
//            }
//        });
//        // 3.获取对那个学生的id
//        List<Integer> studentIds = new ArrayList<>();
//        for (Student student : gradeOneStudents) {
//            studentIds.add(student.getId());
//        }
//        System.out.println(studentIds);
        /*---------------------------- 集合处理：原始方法（结束） -------------------------------*/


        /*---------------------------- 集合处理：Stream 处理（开始） -------------------------------*/
        List<Integer> studentIds = students.stream()
                                           .filter(student -> Grade.FIRST.equals(student.getGrade()))
                                           .sorted(Comparator.comparingInt(Student::getScore))
                                           .map(Student::getId)
                                           .collect(Collectors.toList());
        System.out.println(studentIds);
        /*---------------------------- 集合处理：Stream 处理（结束） -------------------------------*/

    }

}
