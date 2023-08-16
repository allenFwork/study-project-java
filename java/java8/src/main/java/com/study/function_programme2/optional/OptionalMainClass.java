package com.study.function_programme2.optional;

import com.study.function_programme2.stream.Author;
import com.study.function_programme2.stream.Book;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalMainClass {

    public static void main(String[] args) {
        Author author = getAuthor();
        if (author != null) {
            System.out.println(author.getName());
        }
        // 创建Optional对象(方法1)
        Optional<Author> authorOptional = Optional.ofNullable(author);

        // 创建Optional对象(方法2): 创建对象中存储数据不能为空
        authorOptional = Optional.of(new Author());

        // 创建Optional对象(方法3): 创建对象中存储数据为空
        Optional emptyOptional = Optional.empty();

        // 从Optional对象中获取数据并进行操作
        authorOptional.ifPresent(author1 -> System.out.println(author1.getName()));


        // 获取值(方法1):
        Author author1 = authorOptional.orElseGet(new Supplier<Author>() {
            @Override
            public Author get() {
                return new Author(1L, "张三", 33, "一个默认对象", null);
            }
        });
        // lambda表达式简化
        authorOptional.orElseGet(() -> new Author(1L, "张三", 33, "一个默认对象", null));

        try {
            // 获取值(方法2):
            authorOptional.orElseThrow(new Supplier<Throwable>() {
                @Override
                public Throwable get() {
                    return new RuntimeException("数据为空");
                }
            });
            // lambda表达式简化
            authorOptional.orElseThrow(() -> new RuntimeException("数据为空"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // 过滤
        authorOptional.filter(author2 -> author2.getAge() > 18)
                .ifPresent(author2 -> System.out.println(author2.getName()));

        // 判断
        if (authorOptional.isPresent()) {
            // 此时可以使用get方法
            System.out.println(authorOptional.get().getName());
        }

        // 数据转换
        Optional<Author> authorOptional1 = getAuthorOptional();
        Optional<List<Book>> optionalBookList = authorOptional1.map(author22 -> author22.getBooks());


    }

    public static Author getAuthor() {
        Author author = new Author(1L, "蒙多", 33, "一个从才到中明悟哲理的祖安人", null);
        return author;
    }

    public static Optional<Author> getAuthorOptional() {
        Author author = new Author(1L, "蒙多", 33, "一个从才到中明悟哲理的祖安人", null);
        Optional<Author> authorOptional = Optional.ofNullable(author);
        return authorOptional;
    }

}
