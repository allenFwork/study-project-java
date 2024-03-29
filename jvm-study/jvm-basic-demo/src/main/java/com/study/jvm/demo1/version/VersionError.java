package com.study.jvm.demo1.version;

// org.apache.commons 的 commons-lang3 jar包下的类，该类的字节码文件对应的jre版本是1.8
//import org.apache.commons.lang3.RandomStringUtils;

// commons-lang 的 commons-lang jar包下的类，该类的字节码文件对应的jre版本是1.3
import org.apache.commons.lang.RandomStringUtils;

/**
 * jdk版本问题：高版本的class文件，在低版本的jre环境中运行
 */
public class VersionError {
    public static void main(String[] args) {
        // 生成10个字符长度的随机字符串
        String s = RandomStringUtils.randomAlphabetic(10);
        System.out.println(s);
    }
}
