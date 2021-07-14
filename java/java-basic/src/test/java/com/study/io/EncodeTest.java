package com.study.io;

import org.junit.Test;

public class EncodeTest {

    @Test
    public void test(){
        String content = "好人";
        EncodeUtil.toHexString(content);
        System.out.println("=====================================");
        content = "hope";
        EncodeUtil.toHexString(content);
    }

}
