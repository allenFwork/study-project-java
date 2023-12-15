package com.study.springboot.basic.use01;

import org.springframework.context.MessageSource;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * MessageSource接口功能使用：国际化功能
 */
public class TestMessageSource {

    public static void main(String[] args) {

        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("messageSource", MessageSource.class, () -> {
            ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
            ms.setDefaultEncoding("utf-8");
            ms.setBasename("messages");
            return ms;
        });

        context.refresh();

        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));  // Hello
        System.out.println(context.getMessage("hi", null, Locale.CHINESE));  // 你好
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE)); // こんにちは
    }

}
