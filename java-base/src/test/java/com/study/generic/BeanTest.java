package com.study.generic;

import org.junit.Test;

public class BeanTest {

    @Test
    public void test() {
        Bean<String>  bean1 = new Bean<String>();
        Bean<Integer> bean2 = new Bean<Integer>();

        System.out.println(bean1.getClass().getName());
        System.out.println(bean2.getClass().getName());

//        Bean<Number> bean3 = bean1; 报错
    }

}
