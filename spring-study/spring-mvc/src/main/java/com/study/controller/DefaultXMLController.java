package com.study.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultXMLController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // “/index”是试图文件的名字，通过视图解析器会找到 /WEB-INF/index.jsp
        return new ModelAndView("/index", "message", "这是通过xml实现org.springframework.web.servlet.mvc.Controller接口完成的控制器");
    }
}