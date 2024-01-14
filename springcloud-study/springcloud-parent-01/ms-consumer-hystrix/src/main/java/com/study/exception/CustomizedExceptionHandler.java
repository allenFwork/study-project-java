package com.study.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的全局异常处理
 */
@ControllerAdvice
public class CustomizedExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = CustomizedException.class)
    public Object dealException() {
        Map<String, String> map = new HashMap<>();
        map.putIfAbsent("name", "容错处理");
        return map;
    }

}
