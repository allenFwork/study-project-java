package com.study.util.email;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EmailUtilTest {

    @Test
    public void testSendEmail() {
        String subject = "send email test";
        String content = "hello world!";
        List<String> recipient = new ArrayList<>();
        recipient.add("shiwei5@lenovo.com");
        List<String> cc = new ArrayList<>();
        cc.add("songjj10@lenovo.com");
        try {
            EmailUtil.sendMessage(subject, content, recipient, cc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
