package com.study.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用@RestController，那么方法返回结果即使是字符串，
 * 也不会进行视图解析处理，直接将其作为文本对象处理返回给前端 text/plain
 *
 * 使用@Controller，映射请求路径的方法上也没有添加 @ResponseBody相关注解，
 * 那么会对返回的字符串进行视图解析处理，找到了返回给前端类型为 text/html
 */
@RestController // 等价于 @Controller + @ResponseBody
public class RestDemoController {

//    @RequestMapping() // 不设置任何的value值时，该方法会映射所有的路径
    @RequestMapping("/restController")
    // @PostMapping    // Post   请求    @RequestMapping(method = RequestMethod.POST)  Create(C)
    // @GetMapping     // GET    请求    @RequestMapping(method = RequestMethod.GET) Read(R)
    // @PutMapping     // Put    请求    @RequestMapping(method = RequestMethod.PUT) Update(U)
    // @DeleteMapping  // Delete 请求    @RequestMapping(method = RequestMethod.DELETE) Delete(D)
    public String index() {
        return "Hello, World";
    }

    /**
     * 处理页面找不到方法
     * Throwable 是所有的 Exception
     */
    @GetMapping("/404.html")
    public Object handlePageNotFound(HttpServletRequest request, HttpStatus httpStatus, Throwable throwable) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("statusCode", request.getAttribute("javax.servlet.error.status_code"));
        errors.put("requestUri", request.getAttribute("javax.servlet.error.request_uri"));
        return errors;
    }

    /**
     * 处理页面找不到方法
     */
    @GetMapping("/nullPointer")
    public Object handleNullPointerException() {
        throw new NullPointerException("故意抛一个空指针异常！");
    }

}
