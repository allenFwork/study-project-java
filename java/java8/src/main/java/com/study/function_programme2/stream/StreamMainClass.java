package com.study.function_programme2.stream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMainClass {

    public static void main(String[] args) {

        List<Author> authors = getAuthors();
        // System.out.println(authors);

//        authors.stream()    // 把集合转化为流
//                .distinct() // 进行去重
//                .filter(new Predicate<Author>() {
//                    @Override
//                    public boolean test(Author author) {
//                        return author.getAge() < 18;
//                    }
//                })   // 过滤
//                .forEach(new Consumer<Author>() {
//                    @Override
//                    public void accept(Author author) {
//                        System.out.println(author.getName());
//                    }
//                }); // 遍历打印

        authors.stream()                                                 // 把集合转化为流
                .distinct()                                              // 对流中元素进行去重
                .filter(author -> author.getAge() < 18)                  // 过滤
                .forEach(author -> System.out.println(author.getName()));// 遍历打印

        // 注意点：1.一个流对象一旦经过一个终结操作，那么这个流就不能再被使用
        Stream<Author> stream = authors.stream();
        stream.map(author -> author.getName()).forEach(name -> System.out.println(name));
        // 下面这行代码报错：java.lang.IllegalStateException: stream has already been operated upon or closed
        stream.map(author -> author.getName()).forEach(name -> System.out.println(name));

        // createByArray();
        // createByMap();
        // test1();
        // test3();
        // test4();
        // test5();
        // test6();
        // test7();
        // test8();
        // test9();
        // test10();
        // test11();
        // test12();
        // test13();
        // test14();
        // test15();
        // test16();
        // test17();
        // test18();
        // test19();
        // test20();
        // test21();
        // test22();
        test23();
    }

    // 数据初始化
    public static List<Author> getAuthors() {
        Author author1 = new Author(1L, "蒙多", 33, "一个从才到中明悟哲理的祖安人", null);
        Author author2 = new Author(2L, "压缩", 15, "一个从才到中明悟哲理的祖安人", null);
        Author author3 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);
        Author author4 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);
        // 书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L, "刀的两侧是光明与黑暗", "哲学，爱情", 88, "用一把刀划分了爱恨"));
        books1.add(new Book(2L, "一个人不能死在同一把刀下", "个人成长，爱情", 99, "讲述如何从失败中领悟真理"));

        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你想用思维去领悟世界的尽头"));
        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你想用思维去领悟世界的尽头"));
        books2.add(new Book(4L, "吹或不吹", "爱情，个人传记", 56, "一个哲学家的恋爱观注定很难被他所在的时代理解"));

        books3.add(new Book(5L, "你的剑就是我的剑", "爱情", 56, "无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author1.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author1, author2, author3, author4));
        return authorList;
    }

    // 通过数组创建流对象
    public static void createByArray() {
        Integer[] arr = {1, 2, 3, 4, 5};
        // 方法一
        Stream<Integer> stream = Stream.of(arr);
        stream.distinct().forEach(integer -> System.out.println(integer));
        // 方法二
        stream = Arrays.stream(arr);
        stream.distinct().forEach(integer -> System.out.println(integer));
    }

    // 通过Map创建流对象
    public static void createByMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("蜡笔小新", 19);
        map.put("黑子", 17);
        map.put("日向翔阳", 16);

        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Stream<Map.Entry<String, Integer>> stream = entrySet.stream();

        stream.filter(entry -> entry.getValue() > 16).forEach(entry -> System.out.println(entry.getKey()));
    }

    // 中间操作练习1：打印所有姓名长度大于1的作家姓名
    public static void test1() {
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(author -> author.getName().length() > 1)
                .forEach(author -> System.out.println(author.getName())); // forEach终结操作
    }

    // 中间操作练习2：打印所有姓名长度大于1的作家姓名
    public static void test2() {
        List<Author> authors = getAuthors();
        authors.stream()
                /**
                 * map方法：
                 * 1.类型转换，将原本流中Author类型转化为String类型进行操作
                 * 2.该方法是有返回值的，下面的语句时lambda的省略写法，实质就是 “{return author.getName();}”
                 */
                .map(author -> author.getName())
                .forEach(name -> System.out.println(name));


//        authors.stream()
//                .map(new Function<Author, Integer>() {
//                    @Override
//                    public Integer apply(Author author) {
//                        return author.getAge();
//                    }
//                }) // 获取每个年龄
//                .map(integer -> integer + 1) // 每个年龄相加1
//                .forEach(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) {
//
//                    }
//                });
        authors.stream()
                .map(author -> author.getAge()) // 获取每个年龄
                .map(integer -> integer + 1) // 每个年龄相加1
                .forEach(integer -> {

                });
    }

    // 中间操作练习3：打印所有作家的姓名，并且要求其中不能有重复元素
    public static void test3() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .forEach(author -> System.out.println(author.getName()));
    }

    // 中间操作练习4：对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素
    public static void test4() {
        List<Author> authors = getAuthors();

        authors.stream()
                .distinct()
                /**
                 * 使用 sorted() 无参方法时，会出现下面的错误：
                 * java.lang.ClassCastException: com.study.function_programme2.stream.Author cannot be cast to java.lang.Comparable
                 * 解决办法，将 Author类 实现 Comparable<T> 接口，并重写 compareTo 方法                 */
                .sorted()
                .forEach(author -> System.out.println(author));

        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .forEach(author -> System.out.println(author));

    }

    // 中间操作练习5：对流中的元素按照年龄进行降序排序，并且不能有重复的元素，然后打印年龄最大的两个作家姓名
    public static void test5() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .limit(2)
                .forEach(author -> System.out.println(author.getName()));
    }

    // 中间操作练习6：打印除了年龄最大的作家之外的其他作家，要求不能有重复元素，并且按照年龄降序排序
    public static void test6() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .skip(1) // 跳过第一个元素
                .forEach(author -> System.out.println("姓名：" + author.getName() + "， 年龄：" + author.getAge()));
    }

    // 中间操作练习7：打印所有书籍的名字，要求对重复的元素进行去重
    public static void test7() {
        List<Author> authors = getAuthors();
        authors.stream()
                /**
                 * 下面的flatMap方法中：
                 *      1.将获取Author对象的books属性值，该值是一个集合List<Book> books
                 *      2.然后通过books集合对象创建了Stream对象，并将其返回
                 *      3.会自动地将Stream对象合并到一起，形成一个Stream对象向下执行
                 */
                .flatMap(new Function<Author, Stream<Book>>() {
                    @Override
                    public Stream<Book> apply(Author author) {
                        return author.getBooks().stream();
                    }
                })
                .distinct()
                .forEach(new Consumer<Book>() {
                    @Override
                    public void accept(Book book) {
                        System.out.println(book);
                    }
                });
        // 使用lambda表达式
        authors.stream()
                .flatMap((Function<Author, Stream<Book>>) author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book));
        // 使用lambda表达式省略版本
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }

    // 中间操作练习8：打印现有数据的所有分类，要求对分类进行去重，不能出现这种格式：哲学,爱情
    public static void test8() {
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .flatMap(book -> Arrays.stream(book.getCategory().split("，")))
                .distinct()
                .forEach(category -> System.out.println(category));
    }

    // 终结操作练习1：打印现有数据的所有分类，要求对分类进行去重，不能出现这种格式：哲学,爱情
    public static void test9() {
        List<Author> authors = getAuthors();
        authors.stream()
                .map(author -> author.getName())
                .distinct()
                .forEach(name -> System.out.println(name));
    }

    // 终结操作练习2：打印这些作家所出书籍的数目，注意删除重复元素
    public static void test10() {
        List<Author> authors = getAuthors();
        long count = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .count();
        System.out.println(count);
    }

    // 终结操作练习3：分别获取这些作家所出书籍的最高分和最低分，并打印
    public static void test11() {
        List<Author> authors = getAuthors();
        Optional<Integer> max = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .map(book -> book.getScore())
                .max((score1, score2) -> score1 - score2);
        System.out.println(max);
        Optional<Integer> min = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .map(book -> book.getScore())
                .min((score1, score2) -> score1 - score2);
        System.out.println(min);
    }

    // 终结操作练习4：获取一个存放所有作者名字的List集合
    public static void test12() {
        List<Author> authors = getAuthors();
        List<String> nameList = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        System.out.println(nameList);
    }

    // 终结操作练习5：获取一个存放所有书的Set集合
    public static void test13() {
        List<Author> authors = getAuthors();
        Set<Book> bookSet = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toSet());
        System.out.println(bookSet);
    }

    // 终结操作练习6：获取一个Map集合，map的key存放作者名，value为List<Book>
    public static void test14() {
        List<Author> authors = getAuthors();
        Map<String, List<Book>> map = authors.stream()
                .distinct()
                /**
                 * Collectors.toMap方法需要传入两个参数，这两个参数都是Function类型的
                 * 第一个参数用来确定map的key获取逻辑
                 * 第一个参数用来确定map的value获取逻辑
                 */
                .collect(Collectors.toMap(new Function<Author, String>() {
                    @Override
                    public String apply(Author author) {
                        return author.getName();
                    }
                }, new Function<Author, List<Book>>() {
                    @Override
                    public List<Book> apply(Author author) {
                        return author.getBooks();
                    }
                }));
        System.out.println(map);

        // 使用Lambda表达式，并且进行省略
        map = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));
    }

    // 终结操作练习7：判断是否有年龄在29岁以上的作家
    public static void test15() {
        List<Author> authors = getAuthors();
        boolean flag = authors.stream().anyMatch(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() > 29;
            }
        });
        flag = authors.stream().anyMatch(author -> author.getAge() > 29);
        System.out.println(flag);
    }

    // 终结操作练习8：判断是否所有的作家都是成年人
    public static void test16() {
        List<Author> authors = getAuthors();
        boolean flag = authors.stream().allMatch(author -> author.getAge() > 18);
        System.out.println(flag);
    }

    // 终结操作练习9：判断作家是否都没有超过100岁的
    public static void test17() {
        List<Author> authors = getAuthors();
        boolean flag = authors.stream().noneMatch(author -> author.getAge() > 100);
        System.out.println(flag);
    }

    // 终结操作练习10：获取任意一个大于18岁的作家，如果存在就输出他的名字
    public static void test18() {
        List<Author> authors = getAuthors();
        Optional<Author> optional = authors.stream()
                .filter(author -> author.getAge() > 18)
                .findAny();
        // 如果有数据，就打印
        optional.ifPresent(author -> System.out.println(author.getName()));
    }

    // 终结操作练习11：获取一个年龄最小的作家，并输出他的名字
    public static void test19() {
        List<Author> authors = getAuthors();
        Optional<Author> optional = authors.stream()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .findFirst();
        // 如果有数据，就打印
        optional.ifPresent(author -> System.out.println(author.getName()));
    }

    // 终结操作练习12：使用reduce求所有作者年龄的和
    public static void test20() {
        List<Author> authors = getAuthors();
        Integer sum = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce(0, (result, element) -> result + element);
        System.out.println(sum);
    }

    // 终结操作练习13：使用reduce求所有作者中年龄的最大值
    public static void test21() {
        List<Author> authors = getAuthors();
        Integer max = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                // 想要获取年龄的最大值，设置初始值时，传入了一个理论上最小的值(Integer.MIN_VALUE)，该值不可能是最大值
                .reduce(Integer.MIN_VALUE, new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer result, Integer element) {
                        return result > element ? result : element;
                    }
                });
        System.out.println(max);
        // 使用Lambda表达式
        max = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                /**
                 * 第一个形参：每执行一次的结果
                 * 第二个参数：Stream流中每一次遍历的元素
                 */
                .reduce(Integer.MIN_VALUE, (result, element) -> result > element ? result : element);
    }

    // 使用reduce求所有作者中年龄的最小值
    public static void test22() {
        List<Author> authors = getAuthors();
        Integer min = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce(Integer.MAX_VALUE, (result, element) -> result < element ? result : element);
        System.out.println(min);
    }

    // 使用reduce求所有作者中年龄的最小值
    public static void test23() {
        List<Author> authors = getAuthors();
        Optional<Integer> optional = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce((result, element) -> result > element ? element : result);
        optional.ifPresent(age -> System.out.println(age));
    }

}
