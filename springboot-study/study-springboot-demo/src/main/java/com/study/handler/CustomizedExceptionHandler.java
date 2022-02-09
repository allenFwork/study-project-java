package com.study.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理
 *   全局异常统一处理
 */
@ControllerAdvice
public class CustomizedExceptionHandler {

//    @ExceptionHandler(value = CustomizedException.class)
//    @ResponseBody
//    public Map<String, Object> dealException(CustomizedException exception, HttpServletRequest request) {
//        Map<String, Object> info = new HashMap<>();
//        info.put("code", exception.getCode());
//        info.put("msg", exception.getMsg());
//        return info;
//    }

    @ExceptionHandler(value = CustomizedException.class)
    public String dealException(CustomizedException exception, HttpServletRequest request) {
        Map<String, Object> info = new HashMap<>();
        info.put("code", exception.getCode());
        info.put("code", exception.getMsg());
        request.setAttribute("javax.servlet.error.status_code", 500);
        request.setAttribute("ext", info);
        // 重定向,将请求转发到BasicErrorController来处理： /error
        return "forward:/error";
    }

}
