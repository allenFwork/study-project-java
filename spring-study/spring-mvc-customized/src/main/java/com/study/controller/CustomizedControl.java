package com.study.controller;

import com.study.mvc.CustomizedRequestMapping;
import com.study.mvc.FreemarkeView;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomizedControl {

    @CustomizedRequestMapping("/luban.do")
    public FreemarkeView openLubanPage(String name) {
        FreemarkeView freemarkeView = new FreemarkeView("luban.ftl");
        freemarkeView.setModel("name", name);
        return freemarkeView;
    }

    @CustomizedRequestMapping("/hello.do")
    public FreemarkeView open(String name, BlogDoc doc,
                              HttpServletRequest request, HttpServletResponse resp) {
        FreemarkeView freemarkeView = new FreemarkeView("lluban.ft");
        freemarkeView.setModel("name", name);
        return freemarkeView;
    }
}
