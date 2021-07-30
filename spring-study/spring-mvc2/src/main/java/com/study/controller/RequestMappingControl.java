package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestMappingControl {

    @RequestMapping("/hello.do")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("userView");
        mv.addObject("name", "batman");
        return mv;
    }
}
