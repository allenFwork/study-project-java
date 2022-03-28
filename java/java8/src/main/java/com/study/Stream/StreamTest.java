package com.study.Stream;

import java.util.*;
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


        List<Student> list = new ArrayList<>();
        list.add(new Student(5, Grade.FIRST, 40));
        list.add(new Student(1, Grade.FIRST, 60));
        list.add(new Student(4, Grade.FIRST, 30));
        list.add(new Student(2, Grade.SECOND, 80));
        list.add(new Student(3, Grade.THIRD, 100));
        list.add(new Student(5, Grade.FIRST, 40));
        list.add(new Student(6, Grade.FIRST, 70));
        list.add(new Student(7, Grade.SECOND, 10));
        list.add(new Student(7, Grade.SECOND, 60));

        /*---------------------------------- Stream：去重处理（开始） ----------------------------------*/
        List<Student> studentList = list.stream()
                .filter(student -> !Grade.THIRD.equals(student.getGrade()))
                // 去重处理,通过对某个属性进行去重,获取对象集合
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(
                                Comparator.comparing(Student::getId)
                        )), ArrayList::new)
                );
        System.out.println(studentList);

        // distinct的使用
        List<Integer> scoreList = list.stream()
                // 去重处理,通过对某个属性进行去重,只获取该属性值的去重集合
                .map(Student::getScore).distinct()
                .collect(Collectors.toList());
        System.out.println("分数去重后结果集合, scoreList: " + scoreList);

        Set<Integer> scoreSet = list.stream()
                // 去重处理,通过对某个属性进行去重,只获取该属性值的去重集合
                .map(Student::getScore)
                .collect(Collectors.toSet());
        System.out.println("分数去重后结果集合, scoreSet: " + scoreSet);
        /*---------------------------------- Stream：去重处理（结束） ----------------------------------*/

        /**
         * 获取分数最高的学生
         */
        Student maxStudent = list.stream().max(Comparator.comparing(Student::getScore)).orElse(null);
        System.out.println("分数最高的学生: " + maxStudent.getScore());
        /**
         * 获取id值最大的学生
         */
        maxStudent = list.stream().max(Comparator.comparing(Student::getId)).orElse(null);
        System.out.println("id最大的学生: " + maxStudent.getId());

        /**
         * 聚合处理处理获取Map: 学生的id和grade相同的聚合在一起
         * 此时Map的key是student的id_grade字符串
         */
        Map<String, List<Student>> groupMap = list.stream().collect(Collectors.groupingBy(x -> x.getId() + "_" + x.getGrade()));
        List<String> strings = groupMap.entrySet().stream().map(entry -> {
            String key = entry.getKey();
            List<Student> tempStudents = entry.getValue();
            String[] contents = key.split("_");
            int totalScore = 0;
            for (Student student : tempStudents) {
                totalScore += student.getScore();
            }
            return "id为" + contents[0] + ",Grade为" + contents[1] + "的学生的分数总和为" + totalScore;
        }).collect(Collectors.toList());
        System.out.println(strings);

        List<List<Student>> strings2 = groupMap.entrySet().stream().map(entry -> {
            List<Student> stringList = new ArrayList<>();
            Student student1 = new Student(1, Grade.FIRST, 3);
            Student student2 = new Student(1, Grade.SECOND, 3);
            Student student3 = new Student(1, Grade.THIRD, 3);
            stringList.add(student1);
            stringList.add(student2);
            stringList.add(student3);
            return stringList;
        }).collect(Collectors.toList());
        System.out.println(strings);

        /**
         * 测试 list.clear() 方法对 新集合的影响
         * ArrayList的clear方法源码：
         *     public void clear() {
         *         modCount++;
         *
         *         // clear to let GC do its work
         *         for (int i = 0; i < size; i++)
         *             elementData[i] = null;
         *
         *         size = 0;
         *     }
         * 所以通过 list 的 stream() 方法获取的集合，原来集合调用clear方法，对现有集合不会有任何影响
         * 因为 只是将原集合中所有元素设置为了null，但是新的集合其中的元素还是指向对应的对象
         */
        list.clear();
        System.out.println(studentList);

    }

}
