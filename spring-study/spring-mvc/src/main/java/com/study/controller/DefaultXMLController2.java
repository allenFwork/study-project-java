package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultXMLController2 {

    @RequestMapping("/defaultByXml2")
    public String index(Model model){
        model.addAttribute("message","这里是通过注解的方式实现自定义控制器");
        return "index";
    }

}