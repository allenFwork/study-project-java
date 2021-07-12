package com.study.entity;

public class HelloSpring {

    private String name;
    private int sex;
    private Hi hi;

    public HelloSpring() {

    }

    public HelloSpring(String name, int sex) {
        this.name = name;
        this.sex  = sex;
    }

    public static HelloSpring build(String type) {
        if ("A".equals(type)) {
            return new HelloSpring("superman", 1);
        } else if ("B".equals(type)) {
            return new HelloSpring("super girl", 0);
        } else {
            throw new IllegalArgumentException("出现问题");
        }
    }

}
