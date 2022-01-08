package com.study.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesDemo {

    public static void main(String[] args) {
        // 获取当前项目(java_basic项目)路径下的conf.properties，在target目录下
        InputStream inputStream = PropertiesDemo.class.getResourceAsStream("/conf.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String value = properties.getProperty("test_key");
        System.out.println(value);
    }

}
