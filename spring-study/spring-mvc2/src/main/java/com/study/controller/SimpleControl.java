package com.study.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于 SimpleController来寻找HandlerMapping
 */
public class SimpleControl implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("userView");
        mv.addObject("name", "superman is good man");
        return mv;
    }
}
