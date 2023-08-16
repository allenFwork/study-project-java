package com.study.function_programme2.methodReference;

import com.study.function_programme2.stream.Author;
import com.study.function_programme2.stream.StreamMainClass;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

// 方法引用测试
public class MethodReferenceMainClass {

    public static void main(String[] args) {

        // 类引用静态方法
        List<Author> authors = StreamMainClass.getAuthors();
        authors.stream()
                .map(author -> author.getAge())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return String.valueOf(integer);
                    }
                });
        authors.stream()
                .map(author -> author.getAge())
                .map(String::valueOf);

        // 引用对象的实例方法
        StringBuilder stringBuilder = new StringBuilder();
        authors.stream().map(author -> author.getName())
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        stringBuilder.append(s);
                    }
                });
        authors.stream().map(author -> author.getName()).forEach(stringBuilder::append);

        // 引用类的实例方法
        subAuthorName("江户川柯南", new UseString() {
            @Override
            public String use(String str, int start, int length) {
                // 1.调用第一个参数的成员方法, 即str.substring
                // 2.剩余的所有参数按照顺序传入上述的方法中，即 (start, length)
                // 满足上面连个条件，可以使用 引用类的实例方法
                return str.substring(start, length);
            }
        });
        subAuthorName("江户川柯南", String::substring);

        // 引用构造方法
        authors.stream().map(author -> author.getName())
                .map(name -> new StringBuilder(name)).map(sb -> sb.append("后缀").toString())
                .forEach(str -> System.out.println(str));
        authors.stream().map(author -> author.getName())
                .map(StringBuilder::new)
                .map(sb -> sb.append("后缀").toString())
                .forEach(str -> System.out.println(str));

    }

    interface UseString {
        String use(String str, int start, int length);
    }

    public static String subAuthorName(String str, UseString useString) {
        int start = 0;
        int length = 1;
        return useString.use(str, start, length);
    }

}
