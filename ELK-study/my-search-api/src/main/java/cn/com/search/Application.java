package cn.com.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
/*
 * Redundant declaration: @SpringBootApplication already applies given @EnableAutoConfiguration
 * Redundant declaration: @SpringBootApplication already applies given @ComponentScan
 *
 * 解决办法: 将该文件(Application.java)移动到cn.com.search.web包下。
 *      应该是 Application.java文件自带的@SpringBootApplication中包含@ComponentScan，
 *      默认是扫描该类所在的包和子包的，即 @ComponentScan(basePackages = {"cn.com.search"}),所以再写一遍就提示多余的。
 *
 * redundant ：adj. 被裁减的; 多余的; 不需要的;
 */
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"cn.com.search"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}