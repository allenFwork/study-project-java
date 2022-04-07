package com.study.special;

import org.junit.Test;

public class OuterTest {

    @Test
    public void test() {
        Outer outer = new Outer("outer的值");
        outer.executeInnerMethod();
    }

}
